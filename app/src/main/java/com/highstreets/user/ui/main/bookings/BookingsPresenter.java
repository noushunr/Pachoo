package com.highstreets.user.ui.main.bookings;

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

public class BookingsPresenter implements BookingsPresenterInterface {

    private BookingsViewInterface bookingsViewInterface;
    private Context context;

    public BookingsPresenter(Context context, BookingsViewInterface bookingsViewInterface) {
        this.bookingsViewInterface = bookingsViewInterface;
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
                            bookingsViewInterface.onLoadingBookedOffersSuccess(bookedModelList);
                        } else {
                            bookingsViewInterface.onLoadingBookedOffersFailed(Constants.MESSAGE);
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    bookingsViewInterface.onResponseFailed(Constants.MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                if (CommonUtils.isNetworkAvailable(context)) {
                    bookingsViewInterface.onResponseFailed(Constants.ERROR);
                } else {
                    bookingsViewInterface.noInternet();
                }
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        bookingsViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        bookingsViewInterface.dismissProgressIndicator();
    }
}
