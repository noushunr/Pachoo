package com.presentation.app.dealsnest.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.receiver.NetworkChangeReceiver;
import com.presentation.app.dealsnest.ui.dialog_fragment.ProgressDialogFragment;


public abstract class BaseActivity extends AppCompatActivity {

    protected PowerManager.WakeLock mWakeLock;

    public static final int NO_INTERNET_CODE = 122;
    private ProgressDialogFragment progressDialogFragment;
    private NetworkChangeReceiver mNetworkChangeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "my tag: wake up");
        this.mWakeLock.acquire();

        if (setToolbar()) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        progressDialogFragment = new ProgressDialogFragment();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(mNetworkChangeReceiver, intentFilter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showProgress() {
        if (progressDialogFragment != null) {
            progressDialogFragment.show(getSupportFragmentManager(), null);
        }
    }

    public void dismissProgress() {
        if (progressDialogFragment != null) {
            progressDialogFragment.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NO_INTERNET_CODE) {
            if (resultCode == RESULT_OK) {
                reloadPage();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver);
        }
    }


    @Override
    protected void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }

    protected abstract boolean setToolbar();

    public abstract int setLayout();

    public abstract void reloadPage();

}