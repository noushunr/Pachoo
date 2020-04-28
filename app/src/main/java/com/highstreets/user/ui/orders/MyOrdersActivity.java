package com.highstreets.user.ui.orders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrdersActivity extends BaseActivity implements MyOrderViewInterface {

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;

    public static Intent start(Context context){
        return new Intent(context, MyOrdersActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText.setText(R.string.orders);
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_my_orders;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public void onResponseFailed(String message) {

    }

    @Override
    public void onServerError(String message) {

    }

    @Override
    public void dismissProgressIndicator() {
        dismissProgress();
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {
        showProgress();
    }
}
