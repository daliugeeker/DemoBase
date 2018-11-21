package com.leonardo.demobase.views;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.danikula.videocache.HttpProxyCacheServer;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.leonardo.demobase.DemoBaseApplication;
import com.leonardo.demobase.R;
import com.leonardo.demobase.basestructure.IBasePresenter;
import com.leonardo.demobase.contracts.PlayerViewContract;
import com.leonardo.demobase.utils.Tools;
import com.leonardo.demobase.widgets.exo.LeoPlayerControlView;
import com.leonardo.demobase.widgets.exo.LeoPlayerView;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/20
 */

public class PlayerView extends FrameLayout implements PlayerViewContract.IPlayerView {
  private boolean isActive;
  private SimpleExoPlayer simpleExoPlayer;
  private ViewHolder viewHolder;
  private PlayerViewContract.IPlayerPresenter presenter;

  public PlayerView(@NonNull Context context) {
    super(context);
    initialize();
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    isActive = true;
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    isActive = false;
  }

  @Override public void getPresenter(PlayerViewContract.IPlayerPresenter presenter) {
    this.presenter = presenter;
  }

  @Override public boolean isActive() {
    return isActive;
  }

  @Override public void onDataLoaded(Object data) {

  }

  private void initialize() {
    LayoutInflater.from(getContext()).inflate(R.layout.layout_player_demo, this);
    initialVideoPlayer();
    viewHolder = new ViewHolder(this);
    addListeners();
  }

  private void initialVideoPlayer() {
    DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    AdaptiveTrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
    TrackSelector trackSelector = new DefaultTrackSelector(factory);
    LoadControl loadControl = new DefaultLoadControl();
    RenderersFactory renderersFactory = new DefaultRenderersFactory(getContext());
    simpleExoPlayer =
        ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
  }

  private void prepareVideo(String path) {
    HttpProxyCacheServer proxy = DemoBaseApplication.getProxy();
    String proxyPath = proxy.getProxyUrl(path);
    Uri proxyUrl = Uri.parse(proxyPath);
    MediaSource mediaSource = Tools.buildMediaSource(proxyUrl);
    simpleExoPlayer.stop();
    simpleExoPlayer.prepare(mediaSource);
  }

  private void playVideo(String path) {
    playVideo(path, 0);
  }

  private void playVideo(String path, long currentPlayPosition) {
    prepareVideo(path);
    simpleExoPlayer.setPlayWhenReady(true);
    if (0 != currentPlayPosition) {
      simpleExoPlayer.seekTo(currentPlayPosition);
    }
  }


  private void addListeners() {
    viewHolder.playButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if(null!=presenter){
          presenter.loadData();
        }
      }
    });
  }

  class ViewHolder {

    private final View rootView;

    private LeoPlayerView leoPlayerView;
    private LeoPlayerControlView playController;
    private Button playButton;

    ViewHolder(View rootView) {
      this.rootView = rootView;
      this.leoPlayerView = rootView.findViewById(R.id.leo_video_player);
      this.playController = leoPlayerView.getVideoController();
      this.playButton = rootView.findViewById(R.id.play_button);
    }
  }
}
