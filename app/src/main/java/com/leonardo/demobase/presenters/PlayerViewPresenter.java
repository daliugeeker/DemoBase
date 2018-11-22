package com.leonardo.demobase.presenters;

import com.leonardo.demobase.contracts.PlayerViewContract;
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
 * @since 2018/11/20
 */

public class PlayerViewPresenter implements PlayerViewContract.IPlayerPresenter {

  String TAG = DBLog.DB_TAG + "player_view_presenter";

  private PlayerViewContract.IPlayerView attachedView;
  private DownloadTask downloadTask;

  @Override public void onAttachView(PlayerViewContract.IPlayerView view) {
    this.attachedView = view;
    view.getPresenter(this);
  }

  @Override public void onDetachView() {
    attachedView.getPresenter(null);
    this.attachedView = null;
    if (null != downloadTask) downloadTask.pause();
  }

  @Override public PlayerViewContract.IPlayerView getAttachedView() {
    return attachedView;
  }

  @Override public void loadData() {
    final String downloadUrl = "http://cdn.yesincarapi.com/lkmotion/boss/920-0928-无双.mp4";
        //"http://cdn.yesincarapi.com/lkmotion/boss/920-0928-影.mp4";
    GetRequest<File> request = OkGo.get(downloadUrl);
    downloadTask = OkDownload.request("wushuang", request)
        .fileName("无双.mp4")
        .folder(Tools.generateDir())
        .save()
        .register(new DownloadListener("wushuang") {
          @Override public void onStart(Progress progress) {
            DBLog.d(TAG, " download starts");
          }

          @Override public void onProgress(Progress progress) {
            DBLog.d(TAG, " download progress {" +//
                "fraction=" + progress.fraction +//
                ", totalSize=" + progress.totalSize +//
                ", currentSize=" + progress.currentSize +//
                ", speed=" + progress.speed +//
                ", status=" + progress.status +//
                ", priority=" + progress.priority +//
                ", folder=" + progress.folder +//
                ", filePath=" + progress.filePath +//
                ", fileName=" + progress.fileName +//
                ", tag=" + tag + ", url=" + progress.url +//
                '}');
            if (null != attachedView) attachedView.onProgress(progress);
          }

          @Override public void onError(Progress progress) {
            DBLog.d(TAG, " download error " + progress.exception.toString());
          }

          @Override public void onFinish(File file, Progress progress) {
            if (null != attachedView) attachedView.onDataLoaded(progress);
          }

          @Override public void onRemove(Progress progress) {

          }
        });
    downloadTask.start();
  }
}
