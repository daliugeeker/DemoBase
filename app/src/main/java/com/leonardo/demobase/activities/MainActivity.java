package com.leonardo.demobase.activities;

import android.os.Bundle;
import com.leonardo.demobase.R;
import com.leonardo.demobase.contracts.MainPageContract;
import com.leonardo.demobase.presenters.MainPagePresenter;

public class MainActivity extends BaseActivity implements MainPageContract.IMainPageView {

  private boolean isActive;
  private MainPageContract.IMainPagePresenter mainPagePresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mainPagePresenter = new MainPagePresenter();
    mainPagePresenter.onAttachView(this);
  }

  @Override protected void onStart() {
    super.onStart();
    isActive = true;
  }

  @Override protected void onStop(){
    super.onStop();
    isActive = false;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (null != mainPagePresenter) mainPagePresenter.onDetachView();
  }

  @Override public boolean isActive() {
    return isActive;
  }
}
