package com.highstreets.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.highstreets.user.R;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.ui.main.HomeMainActivity;
import com.highstreets.user.ui.login_registration.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
