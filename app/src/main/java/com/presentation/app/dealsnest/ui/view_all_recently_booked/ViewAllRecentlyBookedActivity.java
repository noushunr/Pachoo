package com.presentation.app.dealsnest.ui.view_all_recently_booked;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.adapters.ViewAllRecentlyBookedAdapter;
import com.presentation.app.dealsnest.models.ViewAllRecentlyBooked;
import com.presentation.app.dealsnest.ui.BaseActivity;

import java.util.List;

public class ViewAllRecentlyBookedActivity extends BaseActivity implements ViewAllRecentlyBookedViewInterFace {

    private RecyclerView rvViewAllRecentlyBooked;
    private RecyclerView.LayoutManager layoutManager;
    private ViewAllRecentlyBookedAdapter viewAllRecentlyBookedAdapter;
    private ViewAllRecentlyBookedPresenter viewAllRecentlyBookedPresenter;
    private TextView tvToolbarText;
    private String CITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        CITY = intent.getStringExtra("city");
        viewAllRecentlyBookedPresenter = new ViewAllRecentlyBookedPresenter(this);
        initView();
    }

    private void initView() {

        tvToolbarText = findViewById(R.id.tvToolbarText);
        tvToolbarText.setText("Shoffz");
        rvViewAllRecentlyBooked = findViewById(R.id.view_all_recently_booked);
        layoutManager = new GridLayoutManager(this, 2);
        rvViewAllRecentlyBooked.setLayoutManager(layoutManager);
        rvViewAllRecentlyBooked.setHasFixedSize(false);
        rvViewAllRecentlyBooked.setNestedScrollingEnabled(false);

        getAllRecentlyBooked(CITY);
    }

    private void getAllRecentlyBooked(String city) {
        viewAllRecentlyBookedPresenter.getAllRecentlyBooked(city);
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_view_all_recently_booked;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public void onLoadingAllRecentlyBookedSuccess(List<ViewAllRecentlyBooked> viewAllRecentlyBookedList) {

        viewAllRecentlyBookedAdapter = new ViewAllRecentlyBookedAdapter(this, viewAllRecentlyBookedList);
        rvViewAllRecentlyBooked.setAdapter(viewAllRecentlyBookedAdapter);

    }


    @Override
    public void onLoadingAllRecentlyBookedFailed(String message) {

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
