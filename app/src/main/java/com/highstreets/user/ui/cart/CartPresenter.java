package com.highstreets.user.ui.cart;

import android.content.Context;

import com.google.gson.JsonObject;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.cart.model.CartResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter implements CartPresenterInterface {

    private Context context;
    private CartViewInterface  cartViewInterface;

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
                    cartViewInterface.setCartData(response.body().getCartData());
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }
}