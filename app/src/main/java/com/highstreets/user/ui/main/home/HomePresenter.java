package com.highstreets.user.ui.main.home;

import android.content.Context;

import com.google.gson.JsonIOException;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.Result;
import com.highstreets.user.models.ShopsList;
import com.highstreets.user.ui.main.categories.sub_categories.ShopList;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.highstreets.user.app_pref.SharedPrefs.Keys.TOKEN;

public class HomePresenter implements HomePresenterInterface {

    private HomeViewInterface homeViewInterface;
    private Context context;

    public HomePresenter(Context context, HomeViewInterface homeViewInterface) {
        this.context = context;
        this.homeViewInterface = homeViewInterface;
    }

    @Override
    public void getHomeDetails(String latitude, String longitude) {
        showProgressIndicator();
        ApiClient.getApiInterface().getHomeData("Bearer "+ SharedPrefs.getString(TOKEN,"")).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        ProductResult jsonObject = response.body();
                        if (jsonObject.getSuccess()==1) {

                            homeViewInterface.setCategoryList(jsonObject.getData());
                        } else {
                            homeViewInterface.onServerError("Failed");
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    homeViewInterface.onResponseFailed("Failed");
                }
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                dismissProgressIndicator();
                if (CommonUtils.isNetworkAvailable(context)) {
                    homeViewInterface.onResponseFailed(Constants.ERROR_MESSAGE_SERVER);
                } else {
                    homeViewInterface.noInternet();
                }
            }
        });
    }


    @Override
    public void showProgressIndicator() {
        homeViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        homeViewInterface.dismissProgressIndicator();
    }


}
