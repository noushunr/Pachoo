package com.highstreets.user;

import android.app.Application;

//import com.facebook.FacebookSdk;
import com.highstreets.user.app_pref.SharedPrefs;
//import com.stripe.android.PaymentConfiguration;

public class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    SharedPrefs.initializePreferenceManager(this);
//    FacebookSdk.sdkInitialize(getApplicationContext());
//    PaymentConfiguration.init(this, "pk_test_51Gs1WzCkc2UppVTD0d4R03YxLWehst6opjVgKV1RvfwVIxyZugv2xZHFc1bqpd40DlviwZyth7SYYvbi3Fru7Ht000EZxIBLXK");
  }


}
