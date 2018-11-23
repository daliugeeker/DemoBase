package com.leonardo.demobase.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.leonardo.demobase.utils.Tools;
import com.leonardo.demobase.widgets.exo.DBLog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;
import java.io.File;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/23
 */

public class DownloadService extends Service {
  String TAG = DBLog.DB_TAG + "download_service";
  public static final String DOWNLOAD_URL = "download_url";
  public static final String FILE_NAME = "file_name";
  private DownloadTask downloadTask;

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public void onCreate() {
    super.onCreate();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    if (null != intent) {
      String downloadUrl = intent.getStringExtra(DOWNLOAD_URL);
      String fileName = intent.getStringExtra(FILE_NAME);

      DownloadListener downloadListener = new DownloadListener(fileName) {
        @Override public void onStart(Progress progress) {
          DBLog.e(TAG, "download start: " + progress.fileName);
        }

        @Override public void onProgress(Progress progress) {
          //DBLog.d(TAG, " download progress {" +//
          //    "fraction=" + progress.fraction +//
          //    ", totalSize=" + progress.totalSize +//
          //    ", currentSize=" + progress.currentSize +//
          //    ", speed=" + progress.speed +//
          //    ", status=" + progress.status +//
          //    ", priority=" + progress.priority +//
          //    ", folder=" + progress.folder +//
          //    ", filePath=" + progress.filePath +//
          //    ", fileName=" + progress.fileName +//
          //    ", tag=" + tag + ", url=" + progress.url +//
          //    '}');
        }

        @Override public void onError(Progress progress) {

        }

        @Override public void onFinish(File file, Progress progress) {
          DBLog.e(TAG, "download finish: " + progress.fileName);
        }

        @Override public void onRemove(Progress progress) {

        }
      };
      if (OkDownload.getInstance().hasTask(fileName)) {
        DBLog.e(TAG, "continue the old task of downloading " + fileName);
        downloadTask = OkDownload.getInstance().getTask(fileName);
        downloadTask.register(downloadListener).start();
      } else {
        GetRequest<File> request = OkGo.get(downloadUrl);
        downloadTask = OkDownload.request(fileName, request)
            .fileName(fileName)
            .folder(Tools.generateDir())
            .save()
            .register(downloadListener);
        downloadTask.start();
      }
    }
    return START_NOT_STICKY;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (null != downloadTask) {
      downloadTask.pause();
      OkDownload.getInstance().pauseAll();
      //OkDownload.getInstance().
    }
  }
}
