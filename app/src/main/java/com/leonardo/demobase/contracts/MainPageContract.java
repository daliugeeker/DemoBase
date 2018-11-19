package com.leonardo.demobase.contracts;

import com.leonardo.demobase.basestructure.IBasePresenter;
import com.leonardo.demobase.basestructure.IBaseView;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/16
 */

public interface MainPageContract {
  interface IMainPageView extends IBaseView<IMainPagePresenter> {

  }

  interface IMainPagePresenter extends IBasePresenter<IMainPageView> {

  }
}
