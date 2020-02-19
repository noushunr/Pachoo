package com.presentation.app.dealsnest.ui.reset_password;

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
    public void resetPassword(String register_id, String new_password) {
        ApiClient.getApiInterface().reset_password(register_id, new_password).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject contentResponse;
                if (response.isSuccessful()) {
                    contentResponse = response.body();
                    String status = contentResponse.get("status").getAsString();
                    resetPasswordViewInterface.onResetPasswordSuccess(status);

                } else {
                    resetPasswordViewInterface.failedToResetNewPassword(Constants.MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

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
