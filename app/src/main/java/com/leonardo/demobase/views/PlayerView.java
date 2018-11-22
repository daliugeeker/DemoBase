package com.leonardo.demobase.views;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.danikula.videocache.HttpProxyCacheServer;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
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
import com.leonardo.demobase.widgets.exo.DBLog;
import com.leonardo.demobase.widgets.exo.LeoPlayerControlView;
import com.leonardo.demobase.widgets.exo.LeoPlayerView;
import com.lzy.okgo.model.Progress;
import java.io.File;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/20
 */

public class PlayerView extends FrameLayout implements PlayerViewContract.IPlayerView<Progress> {
  private final Context context;
  private boolean isActive;
  private SimpleExoPlayer simpleExoPlayer;
  private ViewHolder viewHolder;
  private PlayerViewContract.IPlayerPresenter presenter;
  private String TAG = DBLog.DB_TAG + "player_view";
  private Player.EventListener playerEventListener;

  public PlayerView(@NonNull Context context) {
    super(context);
    this.context = context;
    initialize();
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    isActive = true;
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    isActive = false;
    if (null != viewHolder && null != viewHolder.leoPlayerView) {
      simpleExoPlayer.stop();
      simpleExoPlayer.removeListener(playerEventListener);
      simpleExoPlayer.release();
    }
  }

  @Override public void getPresenter(PlayerViewContract.IPlayerPresenter presenter) {
    this.presenter = presenter;
  }

  @Override public boolean isActive() {
    return isActive;
  }

  @Override public void onDataLoaded(Progress progress) {
    String filePath = progress.filePath;
    playVideo(filePath);
  }

  @Override public void onProgress(Progress progress) {
    viewHolder.progress.setText(String.valueOf(progress.fraction));
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
    RenderersFactory renderersFactory = new DefaultRenderersFactory(context);
    simpleExoPlayer =
        ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
  }

  private void prepareVideo(String path) {
    HttpProxyCacheServer proxy = DemoBaseApplication.getProxy();
    String proxyPath = proxy.getProxyUrl(path);
    Uri proxyUrl = Uri.parse(proxyPath);
    Uri uri = Uri.parse(path);
    MediaSource mediaSource = Tools.buildMediaSource(uri);
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

        if (null != presenter) {
          DBLog.d(TAG, "play button clicked");
          presenter.loadData();
        }
      }
    });

    playerEventListener = new Player.DefaultEventListener() {
      @Override public void onPlayerError(ExoPlaybackException error) {
        super.onPlayerError(error);
        DBLog.e(TAG, "current video play error: " + error.toString());
      }

      @Override public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        super.onPlayerStateChanged(playWhenReady, playbackState);
        DBLog.e(TAG, "current video play state: " + playbackState);
      }
    };

    simpleExoPlayer.addListener(playerEventListener);
  }

  class ViewHolder {

    private final View rootView;

    private com.google.android.exoplayer2.ui.PlayerView leoPlayerView;
    //private LeoPlayerControlView playController;
    private Button playButton;
    private TextView progress;

    ViewHolder(View rootView) {
      this.rootView = rootView;
      this.leoPlayerView = rootView.findViewById(R.id.leo_video_player);
      //this.playController = leoPlayerView.getVideoController();
      this.playButton = rootView.findViewById(R.id.play_button);
      this.progress = rootView.findViewById(R.id.progress);
    }
  }
}
