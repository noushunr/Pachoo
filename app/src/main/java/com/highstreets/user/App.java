package com.highstreets.user;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.highstreets.user.app_pref.SharedPrefs;
import com.stripe.android.PaymentConfiguration;

public class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    SharedPrefs.initializePreferenceManager(this);
    FacebookSdk.sdkInitialize(getApplicationContext());
    PaymentConfiguration.init(this, "pk_test_aVTc5ypFfbdwYuCIKKmK3LEh00cnSTtjcm");
  }


}
