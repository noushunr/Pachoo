package com.presentation.app.dealsnest.ui.offer_details;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.OfferDetail;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferDetailPresenter implements OfferDetailPresenterInterface {

    private OfferDetailViewInterface offerDetailViewInterface;

    public OfferDetailPresenter(OfferDetailViewInterface offerDetailViewInterface) {
        this.offerDetailViewInterface = offerDetailViewInterface;
    }

    @Override
    public void getOfferDetails(String merchant_id, String offer_id, String user_id, String latitude, String longitude) {
        showProgressIndicator();
        ApiClient.getApiInterface().getOfferDetails(merchant_id, offer_id, user_id, latitude, longitude).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray offerDetailArray = jsonObject.get("data").getAsJsonArray();
                            List<OfferDetail> offerDetailList = new Gson().fromJson(offerDetailArray, new TypeToken<List<OfferDetail>>() {
                            }.getType());
                            offerDetailViewInterface.setOfferDetail(offerDetailList.get(0));
                            dismissProgressIndicator();
                        } else {
                            offerDetailViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
                            dismissProgressIndicator();
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dismissProgressIndicator();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                offerDetailViewInterface.onResponseFailed(Constants.ERRORS);
            }
        });
    }

    @Override
    public void getAllOfferDetails(String merchantId, String user_id, String latitude, String longitude) {
        showProgressIndicator();
        ApiClient.getApiInterface().getAllOfferDetails(merchantId, user_id, latitude, longitude).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray offerDetailArray = jsonObject.get("data").getAsJsonArray();
                            List<OfferDetail> offerDetailList = new Gson().fromJson(offerDetailArray, new TypeToken<List<OfferDetail>>() {
                            }.getType());
                            offerDetailViewInterface.setOfferDetail(offerDetailList.get(0));
                            dismissProgressIndicator();
                        } else {
                            offerDetailViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
                            dismissProgressIndicator();
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dismissProgressIndicator();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        offerDetailViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        offerDetailViewInterface.dismissProgressIndicator();
    }
}
