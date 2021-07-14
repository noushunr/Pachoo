package com.highstreets.user.ui.place_order;

import android.util.Log;

import com.google.gson.JsonObject;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.Result;
import com.highstreets.user.models.ShopsList;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.cart.model.CartResponse;
import com.highstreets.user.ui.place_order.model.FinalBalanceItem;
import com.highstreets.user.ui.place_order.model.payment.MakePaymentResponse;
import com.highstreets.user.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.highstreets.user.app_pref.SharedPrefs.Keys.TOKEN;

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
        ApiClient.getApiInterface().getCartItems("Bearer "+SharedPrefs.getString(TOKEN,"")).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    ProductResult cartResponse = response.body();
                    if (cartResponse.getSuccess()==1) {
                        if (cartResponse.getData()!=null)
                            placeOrderViewInterface.setCartData(cartResponse.getData().getCartItems());
                    } else {
                        SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, 0);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void placeOrder(String userId,
                           String addressId,
                           String paymentMethod) {
        showProgressIndicator();
        ApiClient.getApiInterface().placeOrder(
                "Bearer "+ SharedPrefs.getString(TOKEN,""),
                addressId,
                paymentMethod
        ).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    placeOrderViewInterface.setPlaceOrderResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void makePayment(String userId,String orderId,String status) {
//        showProgressIndicator();
        ApiClient.getApiInterface().paymentApiResponse(
                "Bearer "+SharedPrefs.getString(TOKEN,""),
                orderId,
                status
        ).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
//                dismissProgressIndicator();
                if (response.isSuccessful()){
                    ProductResult makePaymentResponse = response.body();
                    if (makePaymentResponse!=null && makePaymentResponse.getSuccess()!=null && makePaymentResponse.getSuccess() == 1){
                        placeOrderViewInterface.paymentSuccess(makePaymentResponse);
                    } else {
                        placeOrderViewInterface.paymentError(makePaymentResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
//                dismissProgressIndicator();
            }
        });
    }
}
