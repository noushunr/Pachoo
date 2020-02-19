package com.presentation.app.dealsnest;

import android.app.Application;

import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class App extends Application {


  @Override
  public void onCreate() {
    super.onCreate();
    Fabric.with(this, new Crashlytics());
    GlobalPreferManager.initializePreferenceManager(this);
  }


}
