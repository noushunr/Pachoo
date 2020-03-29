package com.highstreets.user.ui.view_all_deals;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.adapters.ViewAllDealsAdapter;
import com.highstreets.user.models.ViewAllDeals;
import com.highstreets.user.ui.BaseActivity;

import java.util.List;

public class ViewAllDealsActivity extends BaseActivity implements ViewAllDealsViewInterface {

    private RecyclerView rvViewAllDeals;
    private RecyclerView.LayoutManager layoutManager;
    private ViewAllDealsAdapter viewAllDealsAdapter;
    private ViewAllDealsPresenter viewAllDealsPresenter;
    private TextView tvToolbarText;
    private String CITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        CITY = intent.getStringExtra("city");
        viewAllDealsPresenter = new ViewAllDealsPresenter(this);
        initView();
    }

    private void initView() {
        tvToolbarText = findViewById(R.id.tvToolbarText);
        tvToolbarText.setText(getString(R.string.dealsnest));
        rvViewAllDeals = findViewById(R.id.view_all_deals);
        layoutManager = new GridLayoutManager(this, 2);
        rvViewAllDeals.setLayoutManager(layoutManager);
        rvViewAllDeals.setHasFixedSize(false);
        rvViewAllDeals.setNestedScrollingEnabled(false);

        getAllDeals(CITY);
    }

    private void getAllDeals(String city) {
        viewAllDealsPresenter.getAllDeals(city);
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_view_all_deals;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public void onLoadingAllDealsSuccess(List<ViewAllDeals> viewAllDealsList) {
        viewAllDealsAdapter = new ViewAllDealsAdapter(this, viewAllDealsList);
        rvViewAllDeals.setAdapter(viewAllDealsAdapter);
    }

    @Override
    public void onLoadingAllDealsFailed(String message) {

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
