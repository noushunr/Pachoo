package com.presentation.app.dealsnest.ui.notification;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.notification_model;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter implements NotificationPresenterInterface {


    private NotificationViewInterface notificationViewInterface;

    public NotificationPresenter(NotificationViewInterface notificationViewInterface) {
        this.notificationViewInterface = notificationViewInterface;
    }

    @Override
    public void notification() {
        showProgressIndicator();
        ApiClient.getApiInterface().get_notification().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray data = jsonObject.get(Constants.DATA).getAsJsonArray();
                            List<notification_model> notificationModelList = new Gson().fromJson(data, new TypeToken<List<notification_model>>() {
                            }.getType());
                            notificationViewInterface.onNotificationSuccess(notificationModelList);
                            dismissProgressIndicator();
                        } else {
                            notificationViewInterface.failedToNotify(jsonObject.get(Constants.MESSAGE).getAsString());
                            dismissProgressIndicator();
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    notificationViewInterface.onResponseFailed("Failed");
                    dismissProgressIndicator();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                notificationViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        notificationViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        notificationViewInterface.dismissProgressIndicator();
    }
}
