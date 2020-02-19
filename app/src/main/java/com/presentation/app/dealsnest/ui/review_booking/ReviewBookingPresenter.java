package com.presentation.app.dealsnest.ui.review_booking;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.utils.Constants;

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
    public void getReviewBooking(String user_id, String merchant_id, ArrayList<String> offer_id, ArrayList<String> quantity, ArrayList<String> price) {

        String[] offerId = offer_id.toArray(new String[offer_id.size()]);
        String[] cost = price.toArray(new String[price.size()]);
        String[] qty = quantity.toArray(new String[quantity.size()]);

        showProgressIndicator();
        ApiClient.getApiInterface().get_review_booking(user_id, merchant_id, offerId, qty, cost).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {

                            GlobalPreferManager.setString(GlobalPreferManager.Keys.BOOKING_ID, jsonObject.get("booking_id").getAsString());
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
                dismissProgressIndicator();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
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
