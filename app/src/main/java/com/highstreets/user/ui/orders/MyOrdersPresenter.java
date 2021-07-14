package com.highstreets.user.ui.orders;

import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.orders.model.OrdersResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.highstreets.user.app_pref.SharedPrefs.Keys.TOKEN;

public class MyOrdersPresenter implements MyOrdersPresenterInterface {

    private MyOrderViewInterface myOrderViewInterface;

    public MyOrdersPresenter(MyOrderViewInterface myOrderViewInterface) {
        this.myOrderViewInterface = myOrderViewInterface;
    }

    @Override
    public void showProgressIndicator() {
        myOrderViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        myOrderViewInterface.dismissProgressIndicator();
    }

    @Override
    public void getOrders(String userId) {
        showProgressIndicator();
        ApiClient.getApiInterface().getOrders("Bearer "+ SharedPrefs.getString(TOKEN,"")).enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    OrdersResponse ordersResponse = response.body();
                    myOrderViewInterface.setOrderList(ordersResponse.getOrderList());
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }
}
