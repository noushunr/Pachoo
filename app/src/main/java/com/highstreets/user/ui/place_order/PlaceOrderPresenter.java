package com.highstreets.user.ui.place_order;

import android.util.Log;

import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.address.add_address.model.AddressResponse;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.cart.model.CartResponse;
import com.highstreets.user.ui.place_order.model.FinalBalanceItem;
import com.highstreets.user.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderPresenter implements PlaceOrderPresenterInterface {

    private static final String TAG = PlaceOrderPresenter.class.getSimpleName();
    private PlaceOrderViewInterface placeOrderViewInterface;

    public PlaceOrderPresenter(PlaceOrderViewInterface placeOrderViewInterface) {
        this.placeOrderViewInterface = placeOrderViewInterface;
    }

    @Override
    public void showProgressIndicator() {
        placeOrderViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        placeOrderViewInterface.dismissProgressIndicator();
    }

    @Override
    public void getFinalBalance(String userId,
                                String addressId) {
        showProgressIndicator();
        ApiClient.getApiInterface().getFinalBalance(
                userId,
                addressId
        ).enqueue(new Callback<FinalBalanceItem>() {
            @Override
            public void onResponse(Call<FinalBalanceItem> call, Response<FinalBalanceItem> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    placeOrderViewInterface.setFinalBalance(response.body());
                }
            }

            @Override
            public void onFailure(Call<FinalBalanceItem> call, Throwable t) {
                dismissProgressIndicator();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
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
                        placeOrderViewInterface.setCartData(cartResponse.getCartData());
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
    public void placeOrder(String userId,
                           String addressId,
                           String paymentMethod) {
        ApiClient.getApiInterface().placeOrder(
                userId,
                addressId,
                paymentMethod
        ).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    placeOrderViewInterface.setPlaceOrderResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }
}
