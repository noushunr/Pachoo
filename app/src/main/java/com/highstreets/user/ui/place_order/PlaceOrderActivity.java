package com.highstreets.user.ui.place_order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.highstreets.user.R;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.address.add_address.model.AddressResponse;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.address.model.Address;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.cart.model.CartData;
import com.highstreets.user.ui.main.HomeMainActivity;
import com.highstreets.user.ui.place_order.model.FinalBalanceItem;
import com.highstreets.user.ui.review_booking.adapter.ReviewBookingAdapter;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceOrderActivity extends BaseActivity implements PlaceOrderViewInterface{

    private String userId;
    private String addressId;
    private PlaceOrderPresenterInterface placeOrderPresenterInterface;

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;
    @BindView(R.id.tvSubTotal)
    TextView tvSubTotal;
    @BindView(R.id.tvDeliveryCharge)
    TextView tvDeliveryCharge;
    @BindView(R.id.tvServiceCharge)
    TextView tvServiceCharge;
    @BindView(R.id.tvGrandTotal)
    TextView tvGrandTotal;
    @BindView(R.id.btnPlaceOrder)
    Button btnPlaceOrder;
    @BindView(R.id.rvOrderItems)
    RecyclerView rvOrderItems;

    public static Intent start(Context context){
        return new Intent(context, PlaceOrderActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText.setText(R.string.confirm_orders);
        userId = SharedPrefs.getString(SharedPrefs.Keys.USER_ID, "");
        addressId = getIntent().getStringExtra(Constants.ADDRESS_ID);
        rvOrderItems.setLayoutManager(new LinearLayoutManager(this));
        rvOrderItems.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        placeOrderPresenterInterface = new PlaceOrderPresenter(this);
        placeOrderPresenterInterface.getCartProducts(userId);
        btnPlaceOrder.setEnabled(false);
        clickHandles();
    }

    private void clickHandles() {
        btnPlaceOrder.setOnClickListener(view -> {
            placeOrderPresenterInterface.placeOrder(userId, addressId, "card");
        });
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_place_order;
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
    public void setFinalBalance(FinalBalanceItem finalBalanceItem) {
        if (finalBalanceItem != null) {
            String subTotal = getString(R.string.pound_symbol) + finalBalanceItem.getSubtotal();
            String deliveryCharge = getString(R.string.pound_symbol) + finalBalanceItem.getDeliveryCharge();
            String serviceCharge = getString(R.string.pound_symbol) + finalBalanceItem.getServiceCharge();
            String grandTotal = getString(R.string.pound_symbol) + finalBalanceItem.getGrandTotal();
            tvSubTotal.setText(subTotal);
            tvDeliveryCharge.setText(deliveryCharge);
            tvServiceCharge.setText(serviceCharge);
            tvGrandTotal.setText(grandTotal);
            btnPlaceOrder.setEnabled(true);
        }
    }

    @Override
    public void setPlaceOrderResponse(PostResponse postResponse) {
        if (postResponse != null){
            if (postResponse.getStatus().equals(Constants.ERROR)){
                CommonUtils.showToast(this, postResponse.getMessage());
            } else {
                CommonUtils.showToast(this, postResponse.getMessage());
                SharedPrefs.setString(SharedPrefs.Keys.CART_COUNT, "");
                Intent homeIntent = HomeMainActivity.start(this);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);
            }
        }
    }

    @Override
    public void setCartData(CartData cartData) {
        rvOrderItems.setAdapter(new ReviewBookingAdapter(this, cartData.getProductList()));
        placeOrderPresenterInterface.getFinalBalance(userId, addressId);
    }
}
