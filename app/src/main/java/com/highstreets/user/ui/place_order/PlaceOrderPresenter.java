package com.highstreets.user.ui.place_order;

import com.google.gson.JsonObject;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.address.add_address.model.AddressResponse;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.place_order.model.FinalBalanceItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderPresenter implements PlaceOrderPresenterInterface {

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
                if (response.isSuccessful()){
                    placeOrderViewInterface.setFinalBalance(response.body());
                }
            }

            @Override
            public void onFailure(Call<FinalBalanceItem> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void getAddress(String userId, String addressId) {
        ApiClient.getApiInterface().getAddress(
                userId,
                addressId
        ).enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    placeOrderViewInterface.setAddressResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
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
