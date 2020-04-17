package com.highstreets.user;

import android.app.Application;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.highstreets.user.app_pref.GlobalPreferManager;

public class App extends Application {


  @Override
  public void onCreate() {
    super.onCreate();
    GlobalPreferManager.initializePreferenceManager(this);
    FacebookSdk.sdkInitialize(getApplicationContext());
  }


}
