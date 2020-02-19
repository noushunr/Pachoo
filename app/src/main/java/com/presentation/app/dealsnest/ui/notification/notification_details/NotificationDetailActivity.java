package com.presentation.app.dealsnest.ui.notification.notification_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.adapters.MerchantsAdapter;
import com.presentation.app.dealsnest.models.notification_merchant_view_model;
import com.presentation.app.dealsnest.ui.BaseActivity;
import com.presentation.app.dealsnest.ui.dialog_fragment.ProgressDialogFragment;
import com.presentation.app.dealsnest.utils.CommonUtils;

import java.util.List;

public class NotificationDetailActivity extends BaseActivity implements NotificationDetailViewInterface {

    private TextView tvToolbarText;
    private LinearLayout llNoOfferFound;
    private RecyclerView rvViewMerchants;
    private MerchantsAdapter merchantsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String ID;
    private NotificationDetailPresenter notificationDetailPresenter;
    private ProgressDialogFragment progressDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        ID = intent.getStringExtra("id");
        notificationDetailPresenter = new NotificationDetailPresenter(this);
        initView();
    }

    private void initView() {
        tvToolbarText = findViewById(R.id.tvToolbarText);
        tvToolbarText.setText("Shoffz");
        llNoOfferFound = findViewById(R.id.llNoOfferFound);
        rvViewMerchants = findViewById(R.id.view_all_notification_deals);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvViewMerchants.setLayoutManager(layoutManager);
        rvViewMerchants.setHasFixedSize(false);

        getMerchants(ID);
    }

    private void getMerchants(String id) {
        notificationDetailPresenter.viewMerchants(id);
    }


    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_notification_detail;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public void onSuccess(List<notification_merchant_view_model> merchantViewModelArrayList) {
        merchantsAdapter = new MerchantsAdapter(this, merchantViewModelArrayList);
        rvViewMerchants.setAdapter(merchantsAdapter);
        rvViewMerchants.setVisibility(View.VISIBLE);
        llNoOfferFound.setVisibility(View.GONE);
    }

    @Override
    public void onFails(String data) {
        if (data.equals("Error: no result found")) {
            rvViewMerchants.setVisibility(View.GONE);
            llNoOfferFound.setVisibility(View.VISIBLE);

        } else {
            llNoOfferFound.setVisibility(View.GONE);
            rvViewMerchants.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResponseFailed(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onServerError(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void dismissProgressIndicator() {
        try {
            if (progressDialogFragment != null && !isFinishing())
                progressDialogFragment.dismiss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {
        progressDialogFragment = ProgressDialogFragment.newInstance();
        progressDialogFragment.show(getSupportFragmentManager(), "progress_dialog");
    }
}
