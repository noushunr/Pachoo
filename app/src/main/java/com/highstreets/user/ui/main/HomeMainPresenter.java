package com.highstreets.user.ui.main;

import android.util.Log;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeMainPresenter implements HomeMainPresenterInterface {


    @Override
    public void addTokens(String userId, String token, String type) {
        ApiClient.getApiInterface().addTokens(userId, token, type).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            Log.e("Status", jsonObject.get("message").getAsString());
                        } else {
                            Log.e("Status", jsonObject.get("message").getAsString());
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void dismissProgressIndicator() {

    }
}
