package com.leonardo.demobase.presenters;

import com.leonardo.demobase.contracts.MainPageContract;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/16
 */

public class MainPagePresenter implements MainPageContract.IMainPagePresenter {

  private MainPageContract.IMainPageView view;

  @Override public void onAttachView(MainPageContract.IMainPageView view) {
    this.view = view;
  }

  @Override public void onDetachView() {
    view = null;
  }

  @Override public MainPageContract.IMainPageView getAttachedView() {
    return view;
  }
}
