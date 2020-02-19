package com.presentation.app.dealsnest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.ui.home.HomeMainActivity;
import com.presentation.app.dealsnest.ui.login_registration.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (GlobalPreferManager.getBoolean(GlobalPreferManager.Keys.IS_LOGIN, false)) {
            startActivity(new Intent(SplashActivity.this, HomeMainActivity.class));
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

}
