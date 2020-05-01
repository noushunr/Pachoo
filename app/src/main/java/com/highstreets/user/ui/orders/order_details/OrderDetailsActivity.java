package com.highstreets.user.ui.orders.order_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.highstreets.user.R;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.orders.adapter.ShowQRCodeDialogFragment;
import com.highstreets.user.ui.orders.order_details.adapter.OrderProductsAdapter;
import com.highstreets.user.ui.orders.order_details.model.OrderDetails;
import com.highstreets.user.ui.orders.order_details.model.OrderDetailsResponse;
import com.highstreets.user.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailsActivity extends BaseActivity implements OrderDetailsViewInterface{

    private OrderDetailsPresenterInterface orderDetailsPresenterInterface;
    private String userId;
    private String orderId;

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;
    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    @BindView(R.id.ivQRCode)
    ImageView ivQRCode;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvOrderId)
    TextView tvOrderId;
    @BindView(R.id.tvServiceCharge)
    TextView tvServiceCharge;
    @BindView(R.id.tvShippingCharge)
    TextView tvShippingCharge;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvDate)
    TextView tvDate;



    public static Intent start(Context context) {
        return new Intent(context, OrderDetailsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText.setText(R.string.order_details);

        userId = SharedPrefs.getString(SharedPrefs.Keys.USER_ID, "");
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        orderDetailsPresenterInterface = new OrderDetailsPresenter(this);
        orderDetailsPresenterInterface.getOrder(userId, orderId);
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_order_details;
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
    public void setOrderDetails(OrderDetailsResponse orderDetailsResponse) {
        OrderDetails orderDetails = orderDetailsResponse.getOrderDetails();
        String name = orderDetails.getFirstname() +" "+ orderDetails.getLastname();
        tvName.setText(name);
        tvNumber.setText(orderDetails.getTelephone());
        tvEmail.setText(orderDetails.getEmail());
        tvOrderId.setText(orderDetails.getOrderId());
        tvServiceCharge.setText(getString(R.string.pound_symbol) + orderDetails.getServiceCharge());
        tvShippingCharge.setText(getString(R.string.pound_symbol) + orderDetails.getShippingCharge());
        tvTotal.setText(getString(R.string.pound_symbol) + orderDetails.getTotal());
        tvDate.setText(orderDetails.getDateAdded());
        Glide.with(this)
                .load(orderDetails.getQrCodeImage())
                .into(ivQRCode);

        ivQRCode.setOnClickListener(view -> {
            ShowQRCodeDialogFragment.newInstance(orderDetails.getQrCodeImage())
                    .show(getSupportFragmentManager(), null);
        });

        rvProducts.setAdapter(new OrderProductsAdapter(orderDetailsResponse.getProducts()));
    }
}
