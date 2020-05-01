package com.highstreets.user.ui.orders.order_details;

import android.util.Log;

import com.google.gson.JsonObject;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.orders.order_details.model.OrderDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsPresenter implements OrderDetailsPresenterInterface {

    private OrderDetailsViewInterface orderDetailsViewInterface;
    private static final String TAG = "OrderDetailsPresenter";

    OrderDetailsPresenter(OrderDetailsViewInterface orderDetailsViewInterface) {
        this.orderDetailsViewInterface = orderDetailsViewInterface;
    }

    @Override
    public void showProgressIndicator() {
        orderDetailsViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        orderDetailsViewInterface.dismissProgressIndicator();
    }

    @Override
    public void getOrder(String userId, String orderId) {
        showProgressIndicator();
        ApiClient.getApiInterface().getOrder(userId, orderId).enqueue(new Callback<OrderDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    orderDetailsViewInterface.setOrderDetails(response.body());
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                dismissProgressIndicator();
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });
    }
}
