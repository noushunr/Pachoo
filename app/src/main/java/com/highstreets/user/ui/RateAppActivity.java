package com.highstreets.user.ui;

import android.os.Bundle;

import com.highstreets.user.R;
import com.highstreets.user.ui.base.BaseActivity;


public class RateAppActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean setToolbar() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_rate_app;
    }

    @Override
    public void reloadPage() {

    }

}
