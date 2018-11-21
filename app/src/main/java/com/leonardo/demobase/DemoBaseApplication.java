package com.leonardo.demobase;

import android.app.Application;

import android.os.Environment;
import com.danikula.videocache.HttpProxyCacheServer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import java.io.File;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/16
 */

public class DemoBaseApplication extends Application {

    private static DemoBaseApplication application;

    public static DemoBaseApplication getInstance() {
        if (null == application) {
            throw new RuntimeException("Process Initial Error");
        } else return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        configFresco();
        configOkGo();
    }

    private void configOkGo() {
        HttpHeaders headers = new HttpHeaders();
        try {
            OkGo.getInstance().init(this);
            OkGo.getInstance().addCommonHeaders(headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configFresco() {
        ImagePipelineConfig
                pipelineConfig = ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).build();
        Fresco.initialize(this, pipelineConfig);
    }

    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy() {
        DemoBaseApplication app = getInstance();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {

        String filePath =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/demobase_videos";
        File cacheDir = new File(filePath);

        return new HttpProxyCacheServer.Builder(this).maxCacheSize(1024 * 1024 * 1024)  // 1 Gb for cache
            .cacheDirectory(cacheDir).build();
    }
}
