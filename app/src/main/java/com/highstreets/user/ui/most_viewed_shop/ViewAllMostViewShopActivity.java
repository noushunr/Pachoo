package com.highstreets.user.ui.most_viewed_shop;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.adapters.MostlyViewShopAdapter;
import com.highstreets.user.models.MostViewedShop;
import com.highstreets.user.ui.BaseActivity;

import java.util.List;

public class ViewAllMostViewShopActivity extends BaseActivity implements ViewAllMostViewShopActivityViewInterface {
    private RecyclerView rvMostlyViewShop;
    private RecyclerView.LayoutManager layoutManager;
    private MostlyViewShopAdapter mostlyViewShopAdapter;
    private ViewAllMostViewShopActivityPresenter viewAllMostViewShopActivityPresenter;
    private String CITY;
    private TextView tvToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewAllMostViewShopActivityPresenter = new ViewAllMostViewShopActivityPresenter(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        CITY = intent.getStringExtra("city");

        initView();
    }

    private void initView() {
        tvToolbarText = findViewById(R.id.tvToolbarText);
        tvToolbarText.setText(getString(R.string.dealsnest));
        rvMostlyViewShop = findViewById(R.id.view_all_mostly_view_shop);
        layoutManager = new GridLayoutManager(this, 2);
        rvMostlyViewShop.setLayoutManager(layoutManager);
        rvMostlyViewShop.setHasFixedSize(false);
        rvMostlyViewShop.setNestedScrollingEnabled(false);

        getAllMostVisitedShop(CITY);
    }

    private void getAllMostVisitedShop(String city) {
        viewAllMostViewShopActivityPresenter.getAllMostViewedShops(city);
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_view_all_most_view_shop;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public void onLoadingAllShopsSuccess(List<MostViewedShop> mostViewedShopList) {
        mostlyViewShopAdapter = new MostlyViewShopAdapter(this, mostViewedShopList);
        rvMostlyViewShop.setAdapter(mostlyViewShopAdapter);
    }

    @Override
    public void onLoadingAllShopsFailed(String message) {

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
