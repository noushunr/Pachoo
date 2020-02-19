package com.presentation.app.dealsnest.ui.select_location;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.location_model;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectLocationPresenter implements SelectLocationPresenterInterface {

    private SelectLocationViewInterface selectLocationViewInterface;

    public SelectLocationPresenter(SelectLocationViewInterface selectLocationViewInterface) {
        this.selectLocationViewInterface = selectLocationViewInterface;
    }

    @Override
    public void cities() {
        showProgressIndicator();
        ApiClient.getApiInterface().get_cities().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray data = jsonObject.get(Constants.DATA).getAsJsonArray();

                            List<location_model> locationModels = new Gson().fromJson(data, new TypeToken<List<location_model>>() {
                            }.getType());
                            selectLocationViewInterface.onLocationSelectionSuccess(locationModels);
                            dismissProgressIndicator();

                        } else {
                            selectLocationViewInterface.onLocationSelectionFailed("Failed");
                            dismissProgressIndicator();
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    selectLocationViewInterface.onResponseFailed(Constants.MESSAGE);
                    dismissProgressIndicator();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                selectLocationViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        selectLocationViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        selectLocationViewInterface.dismissProgressIndicator();
    }
}
