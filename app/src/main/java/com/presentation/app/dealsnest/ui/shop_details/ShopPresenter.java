package com.presentation.app.dealsnest.ui.shop_details;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.ShopDetail;
import com.presentation.app.dealsnest.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopPresenter implements ShopPresenterInterface {

    private ShopViewInterface shopFragmentViewInterface;

    public ShopPresenter(ShopViewInterface shopFragmentViewInterface) {
        this.shopFragmentViewInterface = shopFragmentViewInterface;
    }

    @Override
    public void getShopDetails(String merchant_id, String user_id, String longitude, String latitude, String city_name) {
        showProgressIndicator();
        ApiClient.getApiInterface().get_shop_details(merchant_id, user_id, longitude, latitude, city_name).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {

                            JsonArray shopDetailsArray = jsonObject.get(Constants.DATA).getAsJsonArray();
                            JsonObject shopDetailObject = shopDetailsArray.get(0).getAsJsonObject();
                            ShopDetail shopDetail = new Gson().fromJson(shopDetailObject, new TypeToken<ShopDetail>() {
                            }.getType());
                            shopFragmentViewInterface.setShopDetail(shopDetail);
                            dismissProgressIndicator();

                        } else {
                            shopFragmentViewInterface.onFailedToLoadShopDetails(Constants.MESSAGE);
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }

                } else {
                    shopFragmentViewInterface.onResponseFailed(Constants.MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                shopFragmentViewInterface.onResponseFailed(Constants.ERROR);
            }
        });
    }

    @Override
    public void addFavouriteShop(String merchant_id, String user_id, String fav_status) {
        showProgressIndicator();
        ApiClient.getApiInterface().add_fav_shop(merchant_id, user_id, fav_status).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            shopFragmentViewInterface.onSuccessToAddFav(jsonObject.get("message").getAsString());
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    shopFragmentViewInterface.onFailedToAddFav(Constants.MESSAGE);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        shopFragmentViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        shopFragmentViewInterface.dismissProgressIndicator();
    }
}
