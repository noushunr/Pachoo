package com.highstreets.user.ui.view_all_deals;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.ViewAllDeals;
import com.highstreets.user.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllDealsPresenter implements ViewAllDealsPresenterInterface {

    private ViewAllDealsViewInterface viewAllDealsViewInterface;

    public ViewAllDealsPresenter(ViewAllDealsViewInterface viewAllDealsViewInterface) {
        this.viewAllDealsViewInterface = viewAllDealsViewInterface;
    }

    @Override
    public void getAllDeals(String city) {
        showProgressIndicator();
        ApiClient.getApiInterface().view_all_deals(city).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {

                            JsonArray viewAllDeals = jsonObject.get(Constants.DATA).getAsJsonArray();
                            List<ViewAllDeals> allDeals = new Gson().fromJson(viewAllDeals, new TypeToken<List<ViewAllDeals>>() {
                            }.getType());
                            viewAllDealsViewInterface.onLoadingAllDealsSuccess(allDeals);
                            dismissProgressIndicator();

                        } else {
                            viewAllDealsViewInterface.onLoadingAllDealsFailed(Constants.MESSAGE);
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }

                } else {
                    viewAllDealsViewInterface.onResponseFailed(Constants.MESSAGE);
                }
                dismissProgressIndicator();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        viewAllDealsViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        viewAllDealsViewInterface.dismissProgressIndicator();
    }
}
