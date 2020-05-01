package com.highstreets.user.ui.cart;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.cart.model.CartResponse;
import com.highstreets.user.ui.cart.model.DeleteCartItemResponse;
import com.highstreets.user.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter implements CartPresenterInterface {

    private Context context;
    private CartViewInterface  cartViewInterface;
    private static final String TAG = "CartPresenter";

    public CartPresenter(Context context) {
        this.context = context;
        if (context instanceof CartViewInterface) {
            cartViewInterface = (CartViewInterface) context;
        }
    }

    @Override
    public void showProgressIndicator() {
        cartViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        cartViewInterface.dismissProgressIndicator();
    }

    @Override
    public void getCartProducts(String userId) {
        showProgressIndicator();
        ApiClient.getApiInterface().getCartProducts(userId).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    CartResponse cartResponse = response.body();
                    if (!cartResponse.getStatus().equals(Constants.ERROR)) {
                        cartViewInterface.setCartData(cartResponse.getCartData());
                    } else {
                        SharedPrefs.setString(SharedPrefs.Keys.CART_COUNT, "");
                    }
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void deleteCart(String userId, String cartId) {
        showProgressIndicator();
        ApiClient.getApiInterface().deleteCart(userId, cartId).enqueue(new Callback<DeleteCartItemResponse>() {
            @Override
            public void onResponse(Call<DeleteCartItemResponse> call, Response<DeleteCartItemResponse> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    cartViewInterface.deleteResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<DeleteCartItemResponse> call, Throwable t) {
                dismissProgressIndicator();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
