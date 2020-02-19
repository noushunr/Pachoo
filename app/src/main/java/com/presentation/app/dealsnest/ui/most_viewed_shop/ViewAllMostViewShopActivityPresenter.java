package com.presentation.app.dealsnest.ui.most_viewed_shop;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.MostViewedShop;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllMostViewShopActivityPresenter implements ViewAllMostViewShopActivityPresenterInterface {

    private ViewAllMostViewShopActivityViewInterface viewAllMostViewShopActivityViewInterface;

    public ViewAllMostViewShopActivityPresenter(ViewAllMostViewShopActivityViewInterface viewAllMostViewShopActivityViewInterface) {
        this.viewAllMostViewShopActivityViewInterface = viewAllMostViewShopActivityViewInterface;
    }

    @Override
    public void getAllMostViewedShops(String city) {
        showProgressIndicator();
        ApiClient.getApiInterface().view_all_mostly_view_shop(city).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray viewAllDeals = jsonObject.get(Constants.DATA).getAsJsonArray();
                            List<MostViewedShop> allDeals = new Gson().fromJson(viewAllDeals, new TypeToken<List<MostViewedShop>>() {
                            }.getType());
                            viewAllMostViewShopActivityViewInterface.onLoadingAllShopsSuccess(allDeals);
                            dismissProgressIndicator();
                        } else {
                            viewAllMostViewShopActivityViewInterface.onLoadingAllShopsFailed("Failed");
                            dismissProgressIndicator();
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    viewAllMostViewShopActivityViewInterface.onResponseFailed("Failed");

                }
                dismissProgressIndicator();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                viewAllMostViewShopActivityViewInterface.onServerError(Constants.ERROR);
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        viewAllMostViewShopActivityViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        viewAllMostViewShopActivityViewInterface.dismissProgressIndicator();
    }
}
