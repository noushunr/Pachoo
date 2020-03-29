package com.highstreets.user;

import android.app.Application;

import com.highstreets.user.app_pref.GlobalPreferManager;

public class App extends Application {


  @Override
  public void onCreate() {
    super.onCreate();
    GlobalPreferManager.initializePreferenceManager(this);
  }


}
