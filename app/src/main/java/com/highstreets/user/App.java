package com.highstreets.user;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.highstreets.user.app_pref.SharedPrefs;

public class App extends Application {


  @Override
  public void onCreate() {
    super.onCreate();
    SharedPrefs.initializePreferenceManager(this);
    FacebookSdk.sdkInitialize(getApplicationContext());
  }


}
