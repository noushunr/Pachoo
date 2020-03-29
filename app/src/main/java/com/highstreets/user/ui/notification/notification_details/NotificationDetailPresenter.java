package com.highstreets.user.ui.notification.notification_details;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.notification_merchant_view_model;
import com.highstreets.user.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationDetailPresenter implements NotificationDetailPresenterInterface {

    private NotificationDetailViewInterface notificationDetailViewInterface;

    public NotificationDetailPresenter(NotificationDetailViewInterface notificationDetailViewInterface) {
        this.notificationDetailViewInterface = notificationDetailViewInterface;
    }


    @Override
    public void viewMerchants(String id) {
        showProgressIndicator();
        ApiClient.getApiInterface().getNotificationDetails(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray data = jsonObject.get(Constants.DATA).getAsJsonArray();
                            List<notification_merchant_view_model> notificationDetails = new Gson().fromJson(data, new TypeToken<List<notification_merchant_view_model>>() {
                            }.getType());
                            notificationDetailViewInterface.onSuccess(notificationDetails);
                            dismissProgressIndicator();
                        } else {
                            notificationDetailViewInterface.onFails(jsonObject.get(Constants.DATA).getAsString());
                            dismissProgressIndicator();
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dismissProgressIndicator();
                    notificationDetailViewInterface.onResponseFailed("Failed");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                notificationDetailViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        notificationDetailViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        notificationDetailViewInterface.dismissProgressIndicator();
    }
}
