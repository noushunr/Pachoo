package com.presentation.app.dealsnest.ui;

import android.os.Bundle;

import com.presentation.app.dealsnest.R;


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
