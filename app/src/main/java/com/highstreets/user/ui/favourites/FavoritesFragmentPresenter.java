package com.highstreets.user.ui.favourites;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.favorites_model;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragmentPresenter implements FavoritesFragmentPresenterInterface {

    private FavoritesFragmentViewInterface favoritesFragmentViewInterface;
    private Context context;

    public FavoritesFragmentPresenter(Context context, FavoritesFragmentViewInterface favoritesFragmentViewInterface) {
        this.favoritesFragmentViewInterface = favoritesFragmentViewInterface;
        this.context = context;
    }

    @Override
    public void getFavouriteShop(String user_id) {
        showProgressIndicator();
        ApiClient.getApiInterface().get_favourite_shops(user_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {

                            JsonArray favArray = jsonObject.get(Constants.DATA).getAsJsonArray();
                            List<favorites_model> favoritesModelList = new Gson().fromJson(favArray, new TypeToken<List<favorites_model>>() {
                            }.getType());
                            favoritesFragmentViewInterface.onLoadingFavouriteSuccess(favoritesModelList);
                        } else {
                            favoritesFragmentViewInterface.onLoadingFavouriteFailed(Constants.MESSAGE);
                        }

                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    favoritesFragmentViewInterface.onResponseFailed(Constants.MESSAGE);
                }
                dismissProgressIndicator();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                if (CommonUtils.isNetworkAvailable(context)) {
                    favoritesFragmentViewInterface.onResponseFailed(Constants.ERROR);
                } else {
                    favoritesFragmentViewInterface.noInternet();
                }
            }
        });
    }

    @Override
    public void deleteFavouriteShop(String fav_id) {
        showProgressIndicator();
        ApiClient.getApiInterface().delete_fav_shop(fav_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {

                            favoritesFragmentViewInterface.onDeletingFavouriteSuccess(Constants.MESSAGE);
                        } else if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.ERROR)) {
                            favoritesFragmentViewInterface.onDeletingFavouriteFailed(Constants.MESSAGE);


                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    favoritesFragmentViewInterface.onDeletingFavouriteFailed(Constants.MESSAGE);
                }
                dismissProgressIndicator();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }


    @Override
    public void showProgressIndicator() {
        favoritesFragmentViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        favoritesFragmentViewInterface.dismissProgressIndicator();
    }
}
