package com.highstreets.user.ui.auth.login_registration;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.Login;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRegisterPresenter implements LoginRegisterPresenterInterface {

    private LoginRegisterViewInterface loginRegisterViewInterface;

    LoginRegisterPresenter(LoginRegisterViewInterface loginRegisterViewInterface) {
        this.loginRegisterViewInterface = loginRegisterViewInterface;
    }

    @Override
    public void userLogin(String email, String password,String token) {
        showProgressIndicator();
        ApiClient.getApiInterface().login(email, password,token).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    ProductResult jsonObject = response.body();
                    if (jsonObject.getData()!=null) {
                        SharedPrefs.setString(SharedPrefs.Keys.TOKEN, jsonObject.getData().getToken());

                        loginRegisterViewInterface.onSighInSuccess(jsonObject.getMessage());
                    } else {
                        loginRegisterViewInterface.failedToSignIn(jsonObject.getMessage());
                    }

                } else {
                    loginRegisterViewInterface.onResponseFailed("Login Failed");
                }
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                dismissProgressIndicator();
                loginRegisterViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
            }
        });
    }

    @Override
    public void otpVerification(String contactNo,String otp, String sessionId) {
        showProgressIndicator();
        ApiClient.getApiInterface().otpVerification(contactNo,otp, sessionId).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    ProductResult jsonObject = response.body();
                    if (jsonObject.getSuccess()==1) {
//                        SharedPrefs.setString(SharedPrefs.Keys.TOKEN, jsonObject.getData().getToken());
                        loginRegisterViewInterface.onOtpVerified(jsonObject.getMessage());
                    } else {
                        loginRegisterViewInterface.failedToSignIn(jsonObject.getMessage());
                    }

                } else {
                    loginRegisterViewInterface.onResponseFailed("Error");
                }
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                dismissProgressIndicator();
                loginRegisterViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
            }
        });
    }


    @Override
    public void userRegister(String firstName,
                             String emailId,
                             String password,
                             String confirmPassword,
                             String gender,
                             String mobile,
                             String token) {
        showProgressIndicator();
        ApiClient.getApiInterface().register(
                firstName,
                emailId,
                password,
                confirmPassword,
                gender,
                mobile,
                token).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        ProductResult jsonObject = response.body();
                        if (jsonObject.getData()!=null) {
                            SharedPrefs.setString(SharedPrefs.Keys.TOKEN, jsonObject.getData().getToken());
                            SharedPrefs.setString(SharedPrefs.Keys.SESSION_ID, jsonObject.getData().getSessionId());
                            loginRegisterViewInterface.onSighInSuccess(jsonObject.getMessage());
                        } else {
                            loginRegisterViewInterface.failedToSignIn(jsonObject.getMessage());
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    loginRegisterViewInterface.onResponseFailed("Failed");
                }

            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                dismissProgressIndicator();
                loginRegisterViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
            }
        });
    }

    @Override
    public void userRegisterSocial(String type,
                                   String profileId,
                                   String name,
                                   final String email,
                                   final String profileImage) {
        showProgressIndicator();
        ApiClient.getApiInterface().registerSocialMedia(
                type,
                profileId,
                name,
                email,
                profileImage).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (Constants.SUCCESS.equals(jsonObject.get(Constants.STATUS).getAsString())) {
                        SharedPrefs.setBoolean(SharedPrefs.Keys.IS_LOGIN, true);
                        SharedPrefs.setString(SharedPrefs.Keys.USER_ID, jsonObject.get("user_id").getAsString());
                        if (jsonObject.has("email"))
                            SharedPrefs.setString(SharedPrefs.Keys.USER_EMAIL, jsonObject.get("email").getAsString());
                        SharedPrefs.setString(SharedPrefs.Keys.USER_FIRST_NAME, jsonObject.get("name").getAsString());
                        loginRegisterViewInterface.onSighInSuccess(jsonObject.get("message").getAsString());
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                loginRegisterViewInterface.failedToSignIn("failed");
            }
        });
    }


    @Override
    public void showProgressIndicator() {
        loginRegisterViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        loginRegisterViewInterface.dismissProgressIndicator();
    }
}
