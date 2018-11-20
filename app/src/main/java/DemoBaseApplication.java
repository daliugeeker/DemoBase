import android.annotation.SuppressLint;
import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.lzy.okserver.OkDownload;

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
  }

  private void configFresco() {
    ImagePipelineConfig
        pipelineConfig = ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).build();
    Fresco.initialize(this, pipelineConfig);
  }
}
