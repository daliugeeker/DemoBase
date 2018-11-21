package com.leonardo.demobase.contracts;

import com.leonardo.demobase.basestructure.IBasePresenter;
import com.leonardo.demobase.basestructure.IBaseView;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/20
 */

public interface PlayerViewContract {
  interface IPlayerView<T> extends IBaseView<IPlayerPresenter> {
    void getPresenter(IPlayerPresenter presenter);
    void onDataLoaded(T data);
  }

  interface IPlayerPresenter extends IBasePresenter<IPlayerView> {
    void loadData();
  }
}
