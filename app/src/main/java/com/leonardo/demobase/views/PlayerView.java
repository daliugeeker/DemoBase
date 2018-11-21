package com.leonardo.demobase.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import com.leonardo.demobase.contracts.PlayerViewContract;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/20
 */

public class PlayerView extends FrameLayout implements PlayerViewContract.IPlayerView {
  private boolean isActive;

  public PlayerView(@NonNull Context context) {
    super(context);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    isActive = true;
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    isActive = false;
  }

  @Override public boolean isActive() {
    return isActive;
  }
}
