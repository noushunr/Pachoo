package com.highstreets.user.ui.review_booking;

import android.util.Log;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.cart.model.CartResponse;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewBookingPresenter implements ReviewBookingPresenterInterface {

    private ReviewBookingViewInterface reviewBookingViewInterface;

    public ReviewBookingPresenter(ReviewBookingViewInterface reviewBookingViewInterface) {
        this.reviewBookingViewInterface = reviewBookingViewInterface;
    }

    @Override
    public void getCartProducts(String userId) {
        showProgressIndicator();
        ApiClient.getApiInterface().getCartProducts(userId).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    reviewBookingViewInterface.setCartData(response.body().getCartData());
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void getReviewBooking(String user_id,
                                 String merchant_id,
                                 ArrayList<String> offer_id,
                                 ArrayList<String> quantity,
                                 ArrayList<String> price) {

        String[] offerId = offer_id.toArray(new String[offer_id.size()]);
        String[] cost = price.toArray(new String[price.size()]);
        String[] qty = quantity.toArray(new String[quantity.size()]);

        showProgressIndicator();
        ApiClient.getApiInterface().confirmBooking(
                user_id,
                merchant_id,
                offerId,
                qty,
                cost).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {

                            SharedPrefs.setString(SharedPrefs.Keys.BOOKING_ID, jsonObject.get("booking_id").getAsString());
                            reviewBookingViewInterface.onLoadingReviewBookingSuccess(jsonObject.get("message").getAsString());

                        } else {
                            reviewBookingViewInterface.onLoadingReviewBookingFailed(Constants.MESSAGE);
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    reviewBookingViewInterface.onResponseFailed(Constants.MESSAGE);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                reviewBookingViewInterface.onLoadingReviewBookingSuccess("Your booking has been confirmed.");

            }
        });
    }

    @Override
    public void showProgressIndicator() {
        reviewBookingViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        reviewBookingViewInterface.dismissProgressIndicator();
    }
}
