package com.highstreets.user.ui.search;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.SearchItem;
import com.highstreets.user.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivityPresenter implements SearchActivityPresenterInterface {

    private SearchActivityViewInterface searchActivityViewInterface;

    public SearchActivityPresenter(SearchActivityViewInterface searchActivityViewInterface) {
        this.searchActivityViewInterface = searchActivityViewInterface;
    }

    @Override
    public void get_search_filter_list(String city_name, final String search_text) {
        ApiClient.getApiInterface().get_search_filter_list(city_name, search_text).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray jsonArray = jsonObject.get(Constants.DATA).getAsJsonArray();
                            List<SearchItem> searchListModelList = new Gson().fromJson(jsonArray, new TypeToken<List<SearchItem>>() {
                            }.getType());

                            searchActivityViewInterface.onSearchListFilterSuccess(searchListModelList);

                        } else {
                            searchActivityViewInterface.onResponseFailed(Constants.MESSAGE);
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    searchActivityViewInterface.onResponseFailed(Constants.MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void dismissProgressIndicator() {

    }
}
