package com.leonardo.demobase.contracts;

import com.leonardo.demobase.basestructure.IBasePresenter;
import com.leonardo.demobase.basestructure.IBaseView;
import com.lzy.okgo.model.Progress;

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
    void onProgress(T progress);
  }

  interface IPlayerPresenter extends IBasePresenter<IPlayerView> {
    void loadData();
  }
}
