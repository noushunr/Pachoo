package com.highstreets.user.ui.forgot_password;

import android.util.Log;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordPresenter implements ForgotPasswordPresenterInterface {

    private ForgotPasswordViewInterface forgotPasswordViewInterface;

    public ForgotPasswordPresenter(ForgotPasswordViewInterface forgotPasswordViewInterface) {
        this.forgotPasswordViewInterface = forgotPasswordViewInterface;
    }

    @Override
    public void forgotPassword(String email) {
        showProgressIndicator();
        ApiClient.getApiInterface().forgotpassword(email).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Response :", response.toString());
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            String message = jsonObject.get("message").getAsString();
                            String id = jsonObject.get("register_id").getAsString();
                            forgotPasswordViewInterface.onResetLinkSent(id, message);
                        } else {
                            forgotPasswordViewInterface.failedToSignIn(jsonObject.get(Constants.MESSAGE).getAsString());
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    forgotPasswordViewInterface.onResponseFailed("Failed");
                }

                dismissProgressIndicator();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                forgotPasswordViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        forgotPasswordViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        forgotPasswordViewInterface.dismissProgressIndicator();
    }
}
