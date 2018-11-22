package com.leonardo.demobase.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.leonardo.demobase.R;
import com.leonardo.demobase.contracts.MainPageContract;
import com.leonardo.demobase.presenters.MainPagePresenter;
import com.leonardo.demobase.presenters.PlayerViewPresenter;
import com.leonardo.demobase.utils.PermissionsChecker;
import com.leonardo.demobase.views.PlayerView;

public class MainActivity extends BaseActivity implements MainPageContract.IMainPageView {

  private boolean isActive;
  private MainPageContract.IMainPagePresenter mainPagePresenter;
  private FrameLayout videoPageContainer;
  private PlayerView videoPlayerView;
  private PlayerViewPresenter playerViewPresenter;

  static String[] PERMISSIONS = new String[] {
      android.Manifest.permission.INTERNET, android.Manifest.permission.READ_EXTERNAL_STORAGE,
      android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
      android.Manifest.permission.ACCESS_NETWORK_STATE
  };
  private PermissionsChecker checker;
  private static final int REQUEST_CODE = 919;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    checkPermissions();
    mainPagePresenter = new MainPagePresenter();
    mainPagePresenter.onAttachView(this);
  }

  private void checkPermissions() {
    checker = new PermissionsChecker(this);
    if (checker.lacksPermissions(PERMISSIONS)) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.requestPermissions(PERMISSIONS, REQUEST_CODE); // 请求权限
      }
    } else {
      configVideoPlayerPage();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (REQUEST_CODE == requestCode) {
      if (checker.lacksPermissionsResult(grantResults)) {
        configVideoPlayerPage();
      }
    }
  }

  @Override protected void onStart() {
    super.onStart();
    isActive = true;
  }

  @Override protected void onStop() {
    super.onStop();
    isActive = false;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (null != mainPagePresenter) mainPagePresenter.onDetachView();
  }

  @Override public boolean isActive() {
    return isActive;
  }

  /**
   * Config Video Player Page
   */
  private void configVideoPlayerPage() {
    videoPageContainer = findViewById(R.id.video_player_page_container);
    videoPlayerView = new PlayerView(this);
    playerViewPresenter = new PlayerViewPresenter();
    playerViewPresenter.onAttachView(videoPlayerView);
    videoPlayerView.setLayoutParams(
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
    videoPlayerView.setFocusable(true);
    videoPlayerView.setFocusableInTouchMode(true);
    videoPageContainer.addView(videoPlayerView);
  }
}
