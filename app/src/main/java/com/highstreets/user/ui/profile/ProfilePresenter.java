package com.highstreets.user.ui.profile;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.ProfileData;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.highstreets.user.app_pref.SharedPrefs.Keys.TOKEN;

public class ProfilePresenter implements ProfilePresenterInterface {

    private ProfileViewInterface profileViewInterface;
    private Context context;

    public ProfilePresenter(Context context, ProfileViewInterface profileViewInterface) {
        this.profileViewInterface = profileViewInterface;
        this.context = context;
    }

    @Override
    public void updateProfile(final String register_id,
                              String firstname,
                              String lastname,
                              String email_id,
                              String mobile,
                              final String profile_pic) {
        showProgressIndicator();
        ApiClient.getApiInterface().update_profile("Bearer "+ SharedPrefs.getString(TOKEN,""),firstname,email_id,lastname, mobile).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get("success").getAsInt()== 1) {
                            String IMG = jsonObject.get("message").getAsString();
                            profileViewInterface.onProfileUpdateSuccess(IMG, IMG);
                        } else {
                            profileViewInterface.failedToUpdateProfile(Constants.MESSAGE);
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    profileViewInterface.onResponseFailed(Constants.MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                if (CommonUtils.isNetworkAvailable(context)) {
                    profileViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
                } else {
                    profileViewInterface.noInternet();
                }
            }
        });
    }

    @Override
    public void loadProfileDetails(final String register_id) {
        showProgressIndicator();
        ApiClient.getApiInterface().get_profile_details("Bearer "+ SharedPrefs.getString(TOKEN,"")).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                ProductResult contentResponse;
                if (response.isSuccessful()) {
                    contentResponse = response.body();
                    profileViewInterface.onLoadingProfileSuccess(contentResponse);
                    dismissProgressIndicator();
                }
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                profileViewInterface.dismissProgressIndicator();
                profileViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
            }
        });
    }


    @Override
    public void showProgressIndicator() {
        profileViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        profileViewInterface.dismissProgressIndicator();
    }
}
