package com.highstreets.user.ui.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.adapters.CartAdapter;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.cart.model.CartData;
import com.highstreets.user.ui.cart.model.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends BaseActivity implements
        CartViewInterface,
        CartAdapter.QuantityChange {

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

    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void incrementCount(Product product) {

    }

    @Override
    public void decrementCount(Product product) {

    }
}
