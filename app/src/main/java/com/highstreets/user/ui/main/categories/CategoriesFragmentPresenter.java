package com.highstreets.user.ui.main.categories;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.Category;
import com.highstreets.user.models.Result;
import com.highstreets.user.models.ShopsList;
import com.highstreets.user.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragmentPresenter implements CategoriesFragmentPresenterInterface {

    private CategoriesFragmentViewInterface categoriesFragmentViewInterface;

    public CategoriesFragmentPresenter(CategoriesFragmentViewInterface categoriesFragmentViewInterface) {
        this.categoriesFragmentViewInterface = categoriesFragmentViewInterface;
    }

    @Override
    public void getCategories(String shopId) {
        showProgressIndicator();
        ApiClient.getApiInterface().getCategoryList(shopId).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        Result jsonObject = response.body();
                        if (jsonObject.getSuccess()== 1) {

                            categoriesFragmentViewInterface.setCategoryList(jsonObject.getData());
                        } else {
                            String message = jsonObject.getMessage();
                            categoriesFragmentViewInterface.onLoadingCategoriesFailed(message);
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    categoriesFragmentViewInterface.onResponseFailed("Failed");
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                dismissProgressIndicator();
                categoriesFragmentViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        categoriesFragmentViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        categoriesFragmentViewInterface.dismissProgressIndicator();
    }
}
