package com.highstreets.user.ui.product;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.CartResponse;
import com.highstreets.user.models.Offer;
import com.highstreets.user.models.OfferDetail;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.Result;
import com.highstreets.user.models.ShopsList;
import com.highstreets.user.models.shop.Shop;
import com.highstreets.user.ui.product.model.AddToCartResponse;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.highstreets.user.app_pref.SharedPrefs.Keys.TOKEN;

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
    public void getAllProducts(String shopId,String categoryId) {
        showProgressIndicator();
        ApiClient.getApiInterface().getProducts("Bearer "+SharedPrefs.getString(TOKEN,""),
                categoryId, "1").enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                if (response.isSuccessful()) {
                    try {
                        ProductResult jsonObject = response.body();
                        if (jsonObject.getSuccess()== 1 && jsonObject.getData()!=null) {
                            if (jsonObject.getData().getCart()!=null){
                                if (jsonObject.getData().getCart().getTotalItems()!=null){
                                    int count = Integer.parseInt(jsonObject.getData().getCart().getTotalItems());
                                    SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, count);
                                }
                                int count = Integer.parseInt(jsonObject.getData().getCart().getTotalItems());
                                SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, count);
                            }
                            shopProductsViewInterface.setProductList(jsonObject.getData().getProductList());
                            dismissProgressIndicator();
                        } else {

                            shopProductsViewInterface.onServerError(jsonObject.getMessage());
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
            public void onFailure(Call<ProductResult> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void addToCart(String shopId, String productId, String quantity) {
        showProgressIndicator();
        ApiClient.getApiInterface().addToCart(
                "Bearer "+SharedPrefs.getString(TOKEN,""),
                quantity,
                shopId).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                if (response.isSuccessful()) {
                    ProductResult addToCartResponse = response.body();
                    dismissProgressIndicator();
                    if (addToCartResponse.getSuccess()==1){
                        shopProductsViewInterface.setAddedToCartSuccess(addToCartResponse);
                    }

                }
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {

            }
        });

    }

    @Override
    public void clearCart(String userId) {
        ApiClient.getApiInterface().clearCart(userId)
                .enqueue(new Callback<AddToCartResponse>() {
                    @Override
                    public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                        if (response.isSuccessful()) {
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
