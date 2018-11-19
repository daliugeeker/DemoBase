package com.leonardo.demobase.basestructure;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/16
 */

public interface IBasePresenter<V extends IBaseView> {
  void onAttachView(V view);
  void onDetachView();
  V getAttachedView();
}
