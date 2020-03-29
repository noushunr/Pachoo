package com.highstreets.user.ui.booked;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.BookedOffers;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookedFragmentPresenter implements BookedFragmentPresenterInterface {

    private BookedFragmentViewInterface bookedFragmentViewInterface;
    private Context context;

    public BookedFragmentPresenter(Context context, BookedFragmentViewInterface bookedFragmentViewInterface) {
        this.bookedFragmentViewInterface = bookedFragmentViewInterface;
        this.context = context;
    }

    @Override
    public void getBookedOffers(String user_id) {
        showProgressIndicator();
        ApiClient.getApiInterface().get_booked_offer(user_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {

                            JsonArray data = jsonObject.get(Constants.DATA).getAsJsonArray();

                            List<BookedOffers> bookedModelList = new Gson().fromJson(data, new TypeToken<List<BookedOffers>>() {
                            }.getType());
                            bookedFragmentViewInterface.onLoadingBookedOffersSuccess(bookedModelList);
                        } else {
                            bookedFragmentViewInterface.onLoadingBookedOffersFailed(Constants.MESSAGE);
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    bookedFragmentViewInterface.onResponseFailed(Constants.MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                if (CommonUtils.isNetworkAvailable(context)) {
                    bookedFragmentViewInterface.onResponseFailed(Constants.ERROR);
                } else {
                    bookedFragmentViewInterface.noInternet();
                }
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        bookedFragmentViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        bookedFragmentViewInterface.dismissProgressIndicator();
    }
}
