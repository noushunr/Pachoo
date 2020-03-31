package com.highstreets.user.ui.profile;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
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
        MultipartBody.Part picprofile = null;
        if (!TextUtils.isEmpty(profile_pic)) {
            File file = new File(profile_pic);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            picprofile = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        }
        Map<String, RequestBody> params = new HashMap<>();
        params.put("register_id", RequestBody.create(MediaType.parse("multipart/form-data"), register_id));
        params.put("firstname", RequestBody.create(MediaType.parse("multipart/form-data"), firstname));
        params.put("lastname", RequestBody.create(MediaType.parse("multipart/form-data"), lastname));
        params.put("email_id", RequestBody.create(MediaType.parse("multipart/form-data"), email_id));
        params.put("mobile", RequestBody.create(MediaType.parse("multipart/form-data"), mobile));
        ApiClient.getApiInterface().update_profile(params, picprofile).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            String IMG = jsonObject.get("image").getAsString();
                            profileViewInterface.onProfileUpdateSuccess("Profile Updated", IMG);
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
        ApiClient.getApiInterface().get_profile_details(register_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject contentResponse;
                if (response.isSuccessful()) {
                    contentResponse = response.body();
                    JsonObject loginData = contentResponse.getAsJsonObject();
                    ProfileData ProfileData = new Gson().fromJson(loginData, new TypeToken<ProfileData>() {
                    }.getType());
                    profileViewInterface.onLoadingProfileSuccess(ProfileData);
                    dismissProgressIndicator();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
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
