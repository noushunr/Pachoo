package com.highstreets.user.ui.orders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.orders.adapter.OrdersAdapter;
import com.highstreets.user.ui.orders.model.Order;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrdersActivity extends BaseActivity implements MyOrderViewInterface {

    private MyOrdersPresenterInterface myOrdersPresenterInterface;

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;
    @BindView(R.id.rvOrders)
    RecyclerView rvOrders;

    public static Intent start(Context context){
        return new Intent(context, MyOrdersActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText.setText(R.string.orders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        myOrdersPresenterInterface =  new MyOrdersPresenter(this);
        myOrdersPresenterInterface.getOrders(SharedPrefs.getString(SharedPrefs.Keys.USER_ID, ""));
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

    @Override
    public void setOrderList(List<Order> orderList) {
        if (orderList != null){
            rvOrders.setAdapter(new OrdersAdapter(orderList));
        }
    }
}
