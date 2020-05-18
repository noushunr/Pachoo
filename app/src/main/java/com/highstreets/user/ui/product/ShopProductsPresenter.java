package com.highstreets.user.ui.product;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.Offer;
import com.highstreets.user.models.OfferDetail;
import com.highstreets.user.ui.product.model.AddToCartResponse;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopProductsPresenter implements ShopProductsPresenterInterface {

    private ShopProductsViewInterface shopProductsViewInterface;
    private int noOfferAddedToCart = 0;

    public ShopProductsPresenter(ShopProductsViewInterface shopProductsViewInterface) {
        this.shopProductsViewInterface = shopProductsViewInterface;
    }

    @Override
    public void getOfferDetails(String merchant_id,
                                String offer_id,
                                String user_id,
                                String latitude,
                                String longitude) {
        showProgressIndicator();
        ApiClient.getApiInterface().getOfferDetails(
                merchant_id,
                offer_id,
                user_id,
                latitude,
                longitude).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray offerDetailArray = jsonObject.get("data").getAsJsonArray();
                            List<OfferDetail> offerDetailList = new Gson().fromJson(offerDetailArray, new TypeToken<List<OfferDetail>>() {
                            }.getType());
                            shopProductsViewInterface.setOfferDetail(offerDetailList.get(0));
                            dismissProgressIndicator();
                        } else {
                            shopProductsViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
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
                shopProductsViewInterface.onResponseFailed(Constants.ERRORS);
            }
        });
    }

    @Override
    public void getAllOfferDetails(String merchantId,
                                   String user_id,
                                   String latitude,
                                   String longitude) {
        showProgressIndicator();
        ApiClient.getApiInterface().getAllOfferDetails(
                merchantId,
                user_id,
                latitude,
                longitude).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray offerDetailArray = jsonObject.get("data").getAsJsonArray();
                            List<OfferDetail> offerDetailList = new Gson().fromJson(offerDetailArray, new TypeToken<List<OfferDetail>>() {
                            }.getType());
                            shopProductsViewInterface.setOfferDetail(offerDetailList.get(0));
                            dismissProgressIndicator();
                        } else {
                            shopProductsViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
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
    public void addToCart(String userId, ArrayList<Offer> offerArrayList){
        showProgressIndicator();
        String city = SharedPrefs.getString(SharedPrefs.Keys.GET_CITY_NAME, "");
        int noOfOffers = offerArrayList.size();
        for (Offer offer : offerArrayList){
            ApiClient.getApiInterface().addToCart(
                    userId,
                    offer.getId(),
                    String.valueOf(offer.getCount()),
                    city).enqueue(new Callback<AddToCartResponse>() {
                @Override
                public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                    if (response.isSuccessful()){
                        AddToCartResponse addToCartResponse = response.body();
                        if (addToCartResponse.getCityStatus().equals(Constants.TRUE)) {
                            noOfferAddedToCart++;
                            if (noOfferAddedToCart == noOfOffers) {
                                dismissProgressIndicator();
                                noOfferAddedToCart = 0;
                                shopProductsViewInterface.setAddedToCartSuccess(addToCartResponse);
                            }
                        } else {
                            dismissProgressIndicator();
                            shopProductsViewInterface.cityChanged(addToCartResponse);
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddToCartResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void clearCart(String userId) {
        ApiClient.getApiInterface().clearCart(userId)
        .enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if (response.isSuccessful()){
                    shopProductsViewInterface.cartCleared(response.body());
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void showProgressIndicator() {
        shopProductsViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        shopProductsViewInterface.dismissProgressIndicator();
    }
}
