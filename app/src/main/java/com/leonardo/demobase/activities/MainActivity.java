package com.leonardo.demobase.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.leonardo.demobase.R;
import com.leonardo.demobase.contracts.MainPageContract;
import com.leonardo.demobase.presenters.MainPagePresenter;
import com.leonardo.demobase.presenters.PlayerViewPresenter;
import com.leonardo.demobase.views.PlayerView;

public class MainActivity extends BaseActivity implements MainPageContract.IMainPageView {

  private boolean isActive;
  private MainPageContract.IMainPagePresenter mainPagePresenter;
  private FrameLayout videoPageContainer;
  private PlayerView videoPlayerView;
  private PlayerViewPresenter playerViewPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mainPagePresenter = new MainPagePresenter();
    mainPagePresenter.onAttachView(this);
    configVideoPlayerPage();
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
