package com.highstreets.user.ui.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.highstreets.user.BuildConfig;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.address.AddressActivity;
import com.highstreets.user.ui.auth.login_registration.RegisterActivity;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.cart.adapter.CartAdapter;
import com.highstreets.user.ui.cart.check_postcode.CheckPostcodeDialogFragment;
import com.highstreets.user.ui.cart.model.CartData;
import com.highstreets.user.ui.cart.model.DeleteCartItemResponse;
import com.highstreets.user.ui.cart.model.Product;
import com.highstreets.user.ui.review_booking.ReviewBookingActivity;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.highstreets.user.app_pref.SharedPrefs.Keys.TOKEN;

public class CartActivity extends BaseActivity implements
        CartViewInterface,
        CartAdapter.QuantityChange,
        CartAdapter.RemoveCartItem {

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
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.tvEmptyText)
    TextView tvEmptyText;
    AlertDialog dialog;
    public static Intent start(Context context){
        return new Intent(context, CartActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText.setText(getString(R.string.cart));
        constraintLayout.setVisibility(View.GONE);
        rvCartList.setLayoutManager(new LinearLayoutManager(this));
        rvCartList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        if (BuildConfig.FLAVOR.equals("Billing")){
            btnPlaceOrder.setText("Print Bill");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
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
    public void setCartData(List<Success> cartData) {
        if (cartData != null) {
            constraintLayout.setVisibility(View.VISIBLE);
            tvEmptyText.setVisibility(View.GONE);
            rvCartList.setAdapter(new CartAdapter(cartData));

            SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, cartData.size());
            double totalPrice = 0;
            for (int i=0;i<cartData.size();i++){
                totalPrice=totalPrice+(Double.parseDouble(cartData.get(i).getSellingPrice()) * Integer.parseInt(cartData.get(i).getQuantity()));
            }
            String subTotal = getString(R.string.pound_symbol) + "10";
            String totalOffer = getString(R.string.pound_symbol) + "15";
            String grandTotal = getString(R.string.pound_symbol) + "25";
            tvSubTotal.setText(subTotal);
            tvTotalOffer.setText(totalOffer);
            tvGrandTotal.setText(getString(R.string.pound_symbol) + totalPrice);

            btnPlaceOrder.setOnClickListener(view -> {
//                startActivityForResult(PaymentOptionsActivity.start(this), SELECT_PAYMENT_OPTION_CODE);
                if (BuildConfig.FLAVOR.equals("Billing")){

                }else {
                    startActivity(AddressActivity.start(this));
                }


//                CheckPostcodeDialogFragment.newInstance().show(getSupportFragmentManager(), null);
            });
        } else {
            constraintLayout.setVisibility(View.GONE);
            tvEmptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void deleteResponse(DeleteCartItemResponse deleteCartItemResponse) {
        SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, deleteCartItemResponse.getCartCount());
        cartPresenterInterface.getCartProducts(SharedPrefs.getString(SharedPrefs.Keys.USER_ID, ""));
    }

    @Override
    public void setCartFailed() {
        rvCartList.setAdapter(new CartAdapter(new ArrayList<>()));
        constraintLayout.setVisibility(View.GONE);
        tvEmptyText.setVisibility(View.VISIBLE);
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

    @Override
    public void remove(String cartId) {
        showProgressIndicator();
        ApiClient.getApiInterface().addToCart(
                "Bearer "+SharedPrefs.getString(TOKEN,""),
                "0",
                cartId).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                if (response.isSuccessful()) {
                    ProductResult addToCartResponse = response.body();
                    dismissProgressIndicator();
                    if (addToCartResponse.getSuccess()==1){
                        cartPresenterInterface.getCartProducts(SharedPrefs.getString(SharedPrefs.Keys.USER_ID, ""));
//                        shopProductsViewInterface.setAddedToCartSuccess(addToCartResponse);
                    }

                }
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
//        cartPresenterInterface.deleteCart(
//                SharedPrefs.getString(SharedPrefs.Keys.USER_ID, ""),
//                cartId);
    }

    @Override
    public void addWish(String cartId) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.fragment_add_wish, null);
        alertDialog.setView(alertDialogView);
//        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        Button btnOk = alertDialogView.findViewById(R.id.ok);
        Button btnCancel = alertDialogView.findViewById(R.id.cancel);
        EditText etWishes = alertDialogView.findViewById(R.id.etWishes);
//        alertDialog.setPositiveButton(android.R.string.ok, null);
        dialog = alertDialog.create();
        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etWishes.getText().toString().isEmpty()){
                    etWishes.requestFocus();
                    CommonUtils.showToast(CartActivity.this, "Wishes is not entered");
                }else {
                    dialog.dismiss();
                    showProgressIndicator();
                    ApiClient.getApiInterface().addWish("Bearer "+SharedPrefs.getString(TOKEN,""),etWishes.getText().toString(),cartId).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            dismissProgressIndicator();
                            if (response.isSuccessful()) {
                                JsonObject addToCartResponse = response.body();
                                if (addToCartResponse!=null && addToCartResponse.get("success").getAsInt() == 1){
                                    cartPresenterInterface.getCartProducts(SharedPrefs.getString(SharedPrefs.Keys.USER_ID, ""));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            dismissProgressIndicator();
                        }
                    });
                }
            }
        });
    }
}
