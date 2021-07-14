package com.highstreets.user.ui.auth.forgot_password;

import android.util.Log;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.ProductResult;
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
        ApiClient.getApiInterface().forgotpassword(email).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                if (response.isSuccessful()) {
                    Log.d("Response :", response.toString());
                    try {
                        ProductResult jsonObject = response.body();
                        if (jsonObject.getSuccess()==1) {
                            String message = jsonObject.getMessage();
                            String id= "";
                            if (jsonObject.getData()!=null)
                                 id = jsonObject.getData().getSessionId();
                            forgotPasswordViewInterface.onResetLinkSent(id, message);
                        } else {
                            forgotPasswordViewInterface.failedToSignIn(jsonObject.getMessage());
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
            public void onFailure(Call<ProductResult> call, Throwable t) {
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
