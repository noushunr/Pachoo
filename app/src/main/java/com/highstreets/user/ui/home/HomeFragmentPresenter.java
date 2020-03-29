package com.highstreets.user.ui.home;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.BottomBanner;
import com.highstreets.user.models.BrandedShop;
import com.highstreets.user.models.Category;
import com.highstreets.user.models.Deal;
import com.highstreets.user.models.MiddleBanner;
import com.highstreets.user.models.MostViewedShop;
import com.highstreets.user.models.RecentlyBookedShop;
import com.highstreets.user.models.Slider;
import com.highstreets.user.models.TopBanner;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

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
