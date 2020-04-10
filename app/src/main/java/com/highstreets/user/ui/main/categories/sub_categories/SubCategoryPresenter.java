package com.highstreets.user.ui.main.categories.sub_categories;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.models.FilterItemModel;
import com.highstreets.user.models.FilterPriceModel;
import com.highstreets.user.models.SubCategory;
import com.highstreets.user.models.shop.ShopBanner;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

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
    public void getSubCategories(String category_id) {
        showProgressIndicator();
        ApiClient.getApiInterface().get_sub_categories(category_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray subCategoryArray = jsonObject.get(Constants.DATA).getAsJsonArray();
                            for (int i = 0; i < subCategoryArray.size(); i++) {
                                JsonObject object = subCategoryArray.get(i).getAsJsonObject();
                                GlobalPreferManager.setString(GlobalPreferManager.Keys.SUB_CATEGORY_ID, object.get("id").getAsString());
                                GlobalPreferManager.setString(GlobalPreferManager.Keys.SUB_CATEGORY_NAME, object.get("sub_category").getAsString());
                                GlobalPreferManager.setString(GlobalPreferManager.Keys.SUB_CATEGORY_CREATED_DATE, object.get("created_date").getAsString());
                                GlobalPreferManager.setString(GlobalPreferManager.Keys.SUB_CATEGORY_UPDATED_DATE, object.get("updated_date").getAsString());
                                GlobalPreferManager.setString(GlobalPreferManager.Keys.SUB_CATEGORY_IMAGE, object.get("subcategory_image").getAsString());
                                GlobalPreferManager.setString(GlobalPreferManager.Keys.SUB_CATEGORY_STATUS, object.get("status").getAsString());
                                GlobalPreferManager.setString(GlobalPreferManager.Keys.SUB_CATEGORY_IP, object.get("ip").getAsString());
                            }

                            List<SubCategory> brandsModelsList = new Gson().fromJson(subCategoryArray, new TypeToken<List<SubCategory>>() {
                            }.getType());
                            subCategoryViewInterface.onLoadingSubCategoriesSuccess(brandsModelsList);

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
            public void onFailure(Call<JsonObject> call, Throwable t) {
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
        ApiClient.getApiInterface().get_shop_list(city, category_id, sub_category_id, latitude, longitude).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray data = jsonObject.get(Constants.DATA).getAsJsonArray();

                            List<ShopBanner> shopList = new Gson().fromJson(data, new TypeToken<List<ShopBanner>>() {
                            }.getType());
                            subCategoryViewInterface.onLoadingShopListSuccess(shopList);

                        } else {
                            subCategoryViewInterface.onFailedToLoadSubCategories(Constants.MESSAGE);
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    subCategoryViewInterface.onResponseFailed(Constants.MESSAGE);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                subCategoryViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void getSortedShopList(String option, String city, String category_id, String sub_category_id, String latitude, String longitude) {
        showProgressIndicator();
        ApiClient.getApiInterface().get_sorted_list(option, city, category_id, sub_category_id, latitude, longitude).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray data = jsonObject.get(Constants.DATA).getAsJsonArray();
                            List<ShopBanner> sortedList = new Gson().fromJson(data, new TypeToken<List<ShopBanner>>() {
                            }.getType());
                            subCategoryViewInterface.onLoadingShopListSuccess(sortedList);
                            dismissProgressIndicator();
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    subCategoryViewInterface.onResponseFailed(Constants.MESSAGE);

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                subCategoryViewInterface.onResponseFailed(Constants.ERROR_MESSAGE_SERVER);
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void getFilterList(String category_id, String sub_category_id, String latitude, String longitude) {
//        showProgressIndicator();
        ApiClient.getApiInterface().get_filter_list(category_id, sub_category_id, latitude, longitude).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonObject object = jsonObject.get(Constants.DATA).getAsJsonObject();

                            JsonObject BrandObject = object.get("Brand").getAsJsonObject();

                            JsonObject PriceObject = object.get("Price").getAsJsonObject();

                            FilterItemModel brandItems = new Gson().fromJson(BrandObject, new TypeToken<FilterItemModel>() {
                            }.getType());

                            subCategoryViewInterface.onLoadingBrandItemsSuccess(brandItems);
                            dismissProgressIndicator();

                            FilterPriceModel priceFilter = new Gson().fromJson(PriceObject, new TypeToken<FilterPriceModel>() {
                            }.getType());

                            subCategoryViewInterface.onLoadingPriceItemsSuccess(priceFilter);


                        } else {
                            subCategoryViewInterface.onResponseFailed("Failed");

                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    subCategoryViewInterface.onResponseFailed(Constants.MESSAGE);

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                subCategoryViewInterface.onResponseFailed(Constants.ERROR_MESSAGE_SERVER);
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void getFilterResult(String category_id, String sub_category_id, String latitude, String longitude, String brand, String price, String city) {
       // showProgressIndicator();
        ApiClient.getApiInterface().get_filter_result(category_id, sub_category_id, latitude, longitude, brand, price, city).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray data = jsonObject.get(Constants.DATA).getAsJsonArray();

                            List<ShopBanner> FilteredList = new Gson().fromJson(data, new TypeToken<List<ShopBanner>>() {
                            }.getType());
                            subCategoryViewInterface.onLoadingShopListSuccess(FilteredList);

                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    subCategoryViewInterface.onResponseFailed(Constants.MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
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
