package com.presentation.app.dealsnest.ui.change_password;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordPresenter implements ChangePasswordPresenterInterface {

    private ChangePasswordViewInterface changePasswordViewInterface;

    public ChangePasswordPresenter(ChangePasswordViewInterface changePasswordViewInterface) {
        this.changePasswordViewInterface = changePasswordViewInterface;
    }

    @Override
    public void changePassword(String register_id, String confirm_password) {
        showProgressIndicator();
        ApiClient.getApiInterface().change_password(register_id, confirm_password).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {

                            changePasswordViewInterface.onPasswordChangeSuccess("Password Changed Successfully");
                            dismissProgressIndicator();
                        } else {
                            changePasswordViewInterface.failedToChangePassword("Old password doesnot match");
                            dismissProgressIndicator();
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    changePasswordViewInterface.onResponseFailed("Failed");
                    dismissProgressIndicator();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                changePasswordViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        changePasswordViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        changePasswordViewInterface.dismissProgressIndicator();
    }
}
