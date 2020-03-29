package com.highstreets.user.ui.view_all_recently_booked;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.ViewAllRecentlyBooked;
import com.highstreets.user.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllRecentlyBookedPresenter implements ViewAllRecentlyBookedPresenterInterface {

    private ViewAllRecentlyBookedViewInterFace viewAllRecentlyBookedViewInterFace;

    public ViewAllRecentlyBookedPresenter(ViewAllRecentlyBookedViewInterFace viewAllRecentlyBookedViewInterFace) {
        this.viewAllRecentlyBookedViewInterFace = viewAllRecentlyBookedViewInterFace;
    }

    @Override
    public void getAllRecentlyBooked(String city) {
        showProgressIndicator();
        ApiClient.getApiInterface().view_all_recently_booked(city).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {


                            JsonArray recentlyBooked = jsonObject.getAsJsonArray(Constants.DATA).getAsJsonArray();
                            List<ViewAllRecentlyBooked> recentlyBookedShopList = new Gson().fromJson(recentlyBooked, new TypeToken<List<ViewAllRecentlyBooked>>() {
                            }.getType());
                            viewAllRecentlyBookedViewInterFace.onLoadingAllRecentlyBookedSuccess(recentlyBookedShopList);
                        } else {
                            viewAllRecentlyBookedViewInterFace.onLoadingAllRecentlyBookedFailed(Constants.MESSAGE);
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    viewAllRecentlyBookedViewInterFace.onResponseFailed(Constants.MESSAGE);
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
        viewAllRecentlyBookedViewInterFace.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        viewAllRecentlyBookedViewInterFace.dismissProgressIndicator();
    }
}
