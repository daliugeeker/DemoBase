package com.leonardo.demobase.presenters;

import com.leonardo.demobase.contracts.PlayerViewContract;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/20
 */

public class PlayerViewPresenter implements PlayerViewContract.IPlayerPresenter {

  private PlayerViewContract.IPlayerView attachedView;

  @Override public void onAttachView(PlayerViewContract.IPlayerView view) {
    this.attachedView = view;
  }

  @Override public void onDetachView() {

  }

  @Override public PlayerViewContract.IPlayerView getAttachedView() {
    return attachedView;
  }
}
