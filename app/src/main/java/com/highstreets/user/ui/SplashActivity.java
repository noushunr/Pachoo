package com.highstreets.user.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.highstreets.user.R;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.auth.login_registration.LoginActivity;
import com.highstreets.user.ui.main.HomeMainActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        if (SharedPrefs.getBoolean(SharedPrefs.Keys.IS_LOGIN, false)) {
            new Handler().postDelayed(() -> {
                startActivity(HomeMainActivity.start(SplashActivity.this,true));
                finish();
            }, SPLASH_TIME_OUT);
        } else {
            new Handler().postDelayed(() -> {
                startActivity(LoginActivity.start(SplashActivity.this));
                finish();
            }, SPLASH_TIME_OUT);
        }

    }

}
