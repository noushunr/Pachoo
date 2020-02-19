package com.presentation.app.dealsnest.ui.home;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.BottomBanner;
import com.presentation.app.dealsnest.models.BrandedShop;
import com.presentation.app.dealsnest.models.Category;
import com.presentation.app.dealsnest.models.Deal;
import com.presentation.app.dealsnest.models.MiddleBanner;
import com.presentation.app.dealsnest.models.MostViewedShop;
import com.presentation.app.dealsnest.models.RecentlyBookedShop;
import com.presentation.app.dealsnest.models.Slider;
import com.presentation.app.dealsnest.models.TopBanner;
import com.presentation.app.dealsnest.utils.CommonUtils;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentPresenter implements HomeFragmentPresenterInterface {

    private HomeFragmentViewInterface homeFragmentViewInterface;
    private Context context;

    public HomeFragmentPresenter(Context context, HomeFragmentViewInterface homeFragmentViewInterface) {
        this.context = context;
        this.homeFragmentViewInterface = homeFragmentViewInterface;
    }

    @Override
    public void getHomeDetails(String cities) {
        showProgressIndicator();
        ApiClient.getApiInterface().get_home_details(cities).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {

                            JsonObject data = jsonObject.get(Constants.DATA).getAsJsonObject();
                            List<Category> categoryList = new Gson().fromJson(data.get("categories").getAsJsonArray(), new TypeToken<List<Category>>() {
                            }.getType());
                            List<Slider> sliderList = new Gson().fromJson(data.get("sliders").getAsJsonArray(), new TypeToken<List<Slider>>() {
                            }.getType());
                            List<Deal> dealList = new Gson().fromJson(data.get("deals").getAsJsonArray(), new TypeToken<List<Deal>>() {
                            }.getType());
                            List<TopBanner> topBannerList = new Gson().fromJson(data.get("top_banners").getAsJsonArray(), new TypeToken<List<TopBanner>>() {
                            }.getType());
                            List<BrandedShop> brandedShopList = new Gson().fromJson(data.get("branded_shops").getAsJsonArray(), new TypeToken<List<BrandedShop>>() {
                            }.getType());
                            List<MiddleBanner> middleBannerList = new Gson().fromJson(data.get("middle_banners").getAsJsonArray(), new TypeToken<List<MiddleBanner>>() {
                            }.getType());
                            List<RecentlyBookedShop> recentlyBookedShopList = new Gson().fromJson(data.get("recently_booked_shops").getAsJsonArray(), new TypeToken<List<RecentlyBookedShop>>() {
                            }.getType());
                            List<BottomBanner> bottomBannerList = new Gson().fromJson(data.get("bottom_banners").getAsJsonArray(), new TypeToken<List<BottomBanner>>() {
                            }.getType());
                            List<MostViewedShop> mostViewedShopList = new Gson().fromJson(data.get("most_viewed_shops").getAsJsonArray(), new TypeToken<List<MostViewedShop>>() {
                            }.getType());
                            homeFragmentViewInterface.setCategoryList(categoryList);
                            homeFragmentViewInterface.setDealList(dealList);
                            homeFragmentViewInterface.setSliderList(sliderList);
                            homeFragmentViewInterface.setTopBannerList(topBannerList);
                            homeFragmentViewInterface.setBrandedShopList(brandedShopList);
                            homeFragmentViewInterface.setMiddleBannerList(middleBannerList);
                            homeFragmentViewInterface.setRecentlyBookedList(recentlyBookedShopList);
                            homeFragmentViewInterface.setBottomBannersList(bottomBannerList);
                            homeFragmentViewInterface.setMostViewedShops(mostViewedShopList);

                        } else {
                            homeFragmentViewInterface.onServerError(jsonObject.get(Constants.MESSAGE).getAsString());
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    homeFragmentViewInterface.onResponseFailed("Failed");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                if (CommonUtils.isNetworkAvailable(context)) {
                    homeFragmentViewInterface.onResponseFailed(Constants.ERROR_MESSAGE_SERVER);
                } else {
                    homeFragmentViewInterface.noInternet();
                }
            }
        });
    }


    @Override
    public void showProgressIndicator() {
        homeFragmentViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        homeFragmentViewInterface.dismissProgressIndicator();
    }


}
