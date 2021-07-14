package com.highstreets.user.ui.main.categories.sub_categories;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.FilterItemModel;
import com.highstreets.user.models.FilterPriceModel;
import com.highstreets.user.models.Result;
import com.highstreets.user.models.ShopsList;
import com.highstreets.user.models.SubCategory;
import com.highstreets.user.models.shop.ShopBanner;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryPresenter implements SubCategoryPresenterInterface {
    private SubCategoryViewInterface subCategoryViewInterface;
    private Context context;

    public SubCategoryPresenter(Context context, SubCategoryViewInterface subCategoryViewInterface) {
        this.context = context;
        this.subCategoryViewInterface = subCategoryViewInterface;
    }

    @Override
    public void getSubCategories(String category_id,String shopId) {
        showProgressIndicator();
        ApiClient.getApiInterface().get_sub_categories(category_id,shopId).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        Result jsonObject = response.body();
                        if (jsonObject.getSuccess()==1) {

                            subCategoryViewInterface.onLoadingSubCategoriesSuccess(jsonObject.getData());

                        } else {
                            subCategoryViewInterface.onFailedToLoadSubCategories("Failed");
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    subCategoryViewInterface.onResponseFailed(Constants.MESSAGE);
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                dismissProgressIndicator();
                if (!CommonUtils.isNetworkAvailable(context)) {
                    subCategoryViewInterface.noInternet();
                } else {
                    subCategoryViewInterface.onResponseFailed(Constants.ERROR_MESSAGE_SERVER);
                }

            }
        });
    }

    @Override
    public void getShopLists(String city, String category_id, String sub_category_id, String latitude, String longitude) {
        showProgressIndicator();
        ApiClient.getApiInterface().get_shop_list(city, category_id, sub_category_id, latitude, longitude).enqueue(new Callback<ShopList>() {
            @Override
            public void onResponse(Call<ShopList> call, Response<ShopList> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        ShopList shopList = response.body();
                        if (shopList.getStatus().equals(Constants.SUCCESS)) {
                            if (shopList.getData()!=null && shopList.getData().size()>0){
                                subCategoryViewInterface.onLoadingShopListSuccess(shopList.getData());
                            }else {
                                subCategoryViewInterface.onLoadingShopListSuccess(new ArrayList<>());
                            }

                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    subCategoryViewInterface.onResponseFailed(Constants.MESSAGE);
                }

            }

            @Override
            public void onFailure(Call<ShopList> call, Throwable t) {
                subCategoryViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void getSortedShopList(String option, String city, String category_id, String sub_category_id, String latitude, String longitude) {
        showProgressIndicator();
        ApiClient.getApiInterface().get_sorted_list(option, city, category_id, sub_category_id, latitude, longitude).enqueue(new Callback<ShopList>() {
            @Override
            public void onResponse(Call<ShopList> call, Response<ShopList> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        ShopList shopList = response.body();
                        if (shopList.getStatus().equals(Constants.SUCCESS)) {
                            if (shopList.getData()!=null && shopList.getData().size()>0){
                                subCategoryViewInterface.onLoadingShopListSuccess(shopList.getData());
                            }else {
                                subCategoryViewInterface.onLoadingShopListSuccess(new ArrayList<>());
                            }

                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    subCategoryViewInterface.onResponseFailed(Constants.MESSAGE);

                }
            }

            @Override
            public void onFailure(Call<ShopList> call, Throwable t) {
                subCategoryViewInterface.onResponseFailed(Constants.ERROR_MESSAGE_SERVER);
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void getFilterList(String category_id, String sub_category_id, String latitude, String longitude) {
//        showProgressIndicator();
        ApiClient.getApiInterface().get_filter_list(category_id, sub_category_id, latitude, longitude).enqueue(new Callback<ShopList>() {
            @Override
            public void onResponse(Call<ShopList> call, Response<ShopList> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        ShopList shopList = response.body();
                        if (shopList.getStatus().equals(Constants.SUCCESS)) {
                            if (shopList.getData()!=null && shopList.getData().size()>0){
                                subCategoryViewInterface.onLoadingShopListSuccess(shopList.getData());
                            }else {
                                subCategoryViewInterface.onLoadingShopListSuccess(new ArrayList<>());
                            }

                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    subCategoryViewInterface.onResponseFailed(Constants.MESSAGE);

                }
            }

            @Override
            public void onFailure(Call<ShopList> call, Throwable t) {
                subCategoryViewInterface.onResponseFailed(Constants.ERROR_MESSAGE_SERVER);
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void getFilterResult(String category_id, String sub_category_id, String latitude, String longitude, String brand, String price, String city) {
       // showProgressIndicator();
        ApiClient.getApiInterface().get_filter_result(category_id, sub_category_id, latitude, longitude, brand, price, city).enqueue(new Callback<ShopList>() {
            @Override
            public void onResponse(Call<ShopList> call, Response<ShopList> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        ShopList shopList = response.body();
                        if (shopList.getStatus().equals(Constants.SUCCESS)) {
                            if (shopList.getData()!=null && shopList.getData().size()>0){
                                subCategoryViewInterface.onLoadingShopListSuccess(shopList.getData());
                            }else {
                                subCategoryViewInterface.onLoadingShopListSuccess(new ArrayList<>());
                            }

                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    subCategoryViewInterface.onResponseFailed(Constants.MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ShopList> call, Throwable t) {
                subCategoryViewInterface.onResponseFailed(Constants.ERROR_MESSAGE_SERVER);
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        subCategoryViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        subCategoryViewInterface.dismissProgressIndicator();
    }
}
