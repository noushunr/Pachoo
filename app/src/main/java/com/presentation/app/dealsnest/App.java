package com.presentation.app.dealsnest;

import android.app.Application;

import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;

public class App extends Application {


  @Override
  public void onCreate() {
    super.onCreate();
    GlobalPreferManager.initializePreferenceManager(this);
  }


}
