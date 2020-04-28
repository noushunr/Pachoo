package com.highstreets.user.ui.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.adapters.CartAdapter;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.address.AddressActivity;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.cart.model.CartData;
import com.highstreets.user.ui.cart.model.Product;
import com.highstreets.user.ui.payment.PaymentOptionsActivity;
import com.highstreets.user.ui.review_booking.ReviewBookingActivity;
import com.highstreets.user.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends BaseActivity implements
        CartViewInterface,
        CartAdapter.QuantityChange {

    private static final int SELECT_PAYMENT_OPTION_CODE = 100;
    private CartPresenterInterface cartPresenterInterface;

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;
    @BindView(R.id.rvCartList)
    RecyclerView rvCartList;
    @BindView(R.id.tvSubTotal)
    TextView tvSubTotal;
    @BindView(R.id.tvTotalOffer)
    TextView tvTotalOffer;
    @BindView(R.id.tvGrandTotal)
    TextView tvGrandTotal;
    @BindView(R.id.btnPlaceOrder)
    Button btnPlaceOrder;

    public static Intent start(Context context){
        return new Intent(context, CartActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText.setText(getString(R.string.cart));
        rvCartList.setLayoutManager(new LinearLayoutManager(this));
        rvCartList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        cartPresenterInterface = new CartPresenter(this);
        cartPresenterInterface.getCartProducts(SharedPrefs.getString(SharedPrefs.Keys.USER_ID, ""));


    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_cart;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public void setCartData(CartData cartData) {
        if (cartData != null) {
            rvCartList.setAdapter(new CartAdapter(cartData.getProductList()));
            String subTotal = getString(R.string.pound_symbol) + cartData.getSubTotal();
            String totalOffer = getString(R.string.pound_symbol) + cartData.getTotalOffer();
            String grandTotal = getString(R.string.pound_symbol) + cartData.getGrandTotal();
            tvSubTotal.setText(subTotal);
            tvTotalOffer.setText(totalOffer);
            tvGrandTotal.setText(grandTotal);

            btnPlaceOrder.setOnClickListener(view -> {
//                startActivityForResult(PaymentOptionsActivity.start(this), SELECT_PAYMENT_OPTION_CODE);
                startActivity(AddressActivity.start(this));
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PAYMENT_OPTION_CODE){
            if (resultCode == RESULT_OK) {
                switch (data.getIntExtra(Constants.PAYMENT_TYPE, 0)) {
                    case Constants.PAYMENT_TYPE_CASH_ON_DELIVERY:
                    case Constants.PAYMENT_TYPE_PAY_NOW:{
                        startActivity(AddressActivity.start(this));
                        break;
                    }
                    case Constants.PAYMENT_TYPE_COLLECT_FROM_SHOP: {
                        startActivity(ReviewBookingActivity.start(this));
                        break;
                    }
                }
            }
        }
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
    public void incrementCount(Product product) {

    }

    @Override
    public void decrementCount(Product product) {

    }
}
