package com.leonardo.demobase.basestructure;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/15
 */

public interface IBaseView<P extends IBasePresenter> {
  //void getPresenter(P presenter);
  boolean isActive();
}
