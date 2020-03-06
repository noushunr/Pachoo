package com.presentation.app.dealsnest.ui.coupons;

import android.content.Context;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.Coupon;
import com.presentation.app.dealsnest.models.FavoriteCouponBooking;
import com.presentation.app.dealsnest.utils.CommonUtils;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponsPresenter implements CouponsPresenterInterface {

    private Context context;
    private CouponsViewInterface couponsViewInterface;

    public CouponsPresenter(Fragment fragment, Context context) {
        this.context = context;
        if (fragment instanceof CouponsViewInterface) {
            couponsViewInterface = (CouponsViewInterface) fragment;
        }
    }

    @Override
    public void showProgressIndicator() {
        couponsViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        couponsViewInterface.dismissProgressIndicator();
    }

    @Override
    public void getCoupons(String city, String userId) {
        showProgressIndicator();
        ApiClient.getApiInterface().getCoupons(city, userId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                        JsonArray jsonArray = jsonObject.get(Constants.DATA).getAsJsonArray();
                        List<Coupon> couponList = new Gson().fromJson(jsonArray, new TypeToken<List<Coupon>>() {
                        }.getType());
                        couponsViewInterface.setCoupons(couponList);
                    } else {
                        couponsViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                if (CommonUtils.isNetworkAvailable(context)) {
                    couponsViewInterface.onResponseFailed("Response failed");
                } else {
                    couponsViewInterface.noInternet();
                }

            }
        });
    }

    @Override
    public void addFavoriteCoupon(String userId, String couponId) {
        ApiClient.getApiInterface().addFavoriteCoupons(userId, couponId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    FavoriteCouponBooking favoriteCoupon = new Gson().fromJson(jsonObject, new TypeToken<FavoriteCouponBooking>() {
                    }.getType());
                    couponsViewInterface.onFavoritesAdded(favoriteCoupon);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void couponBooking(String userId, String couponId) {
        showProgressIndicator();
        ApiClient.getApiInterface().couponBooking(userId, couponId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    FavoriteCouponBooking couponBooking = new Gson().fromJson(jsonObject, new TypeToken<FavoriteCouponBooking>() {
                    }.getType());
                    couponsViewInterface.onBooked(couponBooking);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }
}
