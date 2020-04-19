package com.highstreets.user.ui.auth.login_registration;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
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
    public void userLogin(String email, String password) {
        showProgressIndicator();
        ApiClient.getApiInterface().login(email, password).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                        SharedPrefs.setString(SharedPrefs.Keys.USER_ID, jsonObject.get("user_id").getAsString());
                        SharedPrefs.setString(SharedPrefs.Keys.USER_FIRST_NAME, jsonObject.get("firstname").getAsString());
                        SharedPrefs.setString(SharedPrefs.Keys.USER_LAST_NAME, jsonObject.get("lastname").getAsString());
                        SharedPrefs.setString(SharedPrefs.Keys.USER_EMAIL, jsonObject.get("email_id").getAsString());
                        SharedPrefs.setString(SharedPrefs.Keys.USER_MOBILE, jsonObject.get("mobile").getAsString());
                        SharedPrefs.setString(SharedPrefs.Keys.USER_GENDER, jsonObject.get("gender").getAsString());
                        loginRegisterViewInterface.onSighInSuccess("Login Success");
                    } else {
                        loginRegisterViewInterface.failedToSignIn(jsonObject.get(Constants.MESSAGE).getAsString());
                    }

                } else {
                    loginRegisterViewInterface.onResponseFailed("Failed");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                loginRegisterViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
            }
        });
    }


    @Override
    public void userRegister(String firstName,
                             String lastName,
                             String emailId,
                             String password,
                             String confirmPassword,
                             String mobile,
                             String gender) {
        showProgressIndicator();
        ApiClient.getApiInterface().register(
                firstName,
                lastName,
                emailId,
                password,
                confirmPassword,
                mobile,
                gender).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            SharedPrefs.setString(SharedPrefs.Keys.USER_ID, jsonObject.get("register_id").getAsString());
                            loginRegisterViewInterface.onSighInSuccess("Login Successfully");
                        } else {
                            loginRegisterViewInterface.failedToSignIn(jsonObject.get(Constants.MESSAGE).getAsString());
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    loginRegisterViewInterface.onResponseFailed("Failed");
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
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
