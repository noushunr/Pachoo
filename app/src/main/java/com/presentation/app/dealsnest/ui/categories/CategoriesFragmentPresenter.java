package com.presentation.app.dealsnest.ui.categories;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.Category;
import com.presentation.app.dealsnest.utils.Constants;

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
    public void getCategories(String cities) {
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
                            //Categories Array
                            List<Category> categoryList = new Gson().fromJson(data.get("categories").getAsJsonArray(), new TypeToken<List<Category>>() {
                            }.getType());
                            categoriesFragmentViewInterface.setCategoryList(categoryList);
                        } else {
                            categoriesFragmentViewInterface.onLoadingCategoriesFailed(jsonObject.get(Constants.MESSAGE).getAsString());
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    categoriesFragmentViewInterface.onResponseFailed("Failed");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
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
