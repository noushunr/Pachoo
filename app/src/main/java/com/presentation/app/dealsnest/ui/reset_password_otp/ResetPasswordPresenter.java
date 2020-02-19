package com.presentation.app.dealsnest.ui.reset_password_otp;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordPresenter implements ResetPasswordPresenterInterface {

    private ResetPasswordViewInterface resetPasswordViewInterface;

    public ResetPasswordPresenter(ResetPasswordViewInterface resetPasswordViewInterface) {
        this.resetPasswordViewInterface = resetPasswordViewInterface;
    }

    @Override
    public void verify_password(final String reset_key, final String register_id) {
        ApiClient.getApiInterface().verify_password(reset_key, register_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            resetPasswordViewInterface.onResetSuccess("Verified Successfully");
                        } else {
                            resetPasswordViewInterface.failedToReset("Failed To Reset");
                        }

                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                resetPasswordViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        resetPasswordViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        resetPasswordViewInterface.dismissProgressIndicator();
    }
}
