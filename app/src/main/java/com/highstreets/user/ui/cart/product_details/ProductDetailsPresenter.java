package com.highstreets.user.ui.cart.product_details;

import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.cart.product_details.model.ProductDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsPresenter implements ProductDetailsPresenterInterface {

    private ProductDetailsViewInterface productDetailsViewInterface;

    public ProductDetailsPresenter(ProductDetailsViewInterface productDetailsViewInterface) {
        this.productDetailsViewInterface = productDetailsViewInterface;
    }

    @Override
    public void showProgressIndicator() {
        productDetailsViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        productDetailsViewInterface.dismissProgressIndicator();
    }

    @Override
    public void getProduct(String productId) {
        showProgressIndicator();
        ApiClient.getApiInterface().getProduct(productId).enqueue(new Callback<ProductDetailsResponse>() {
            @Override
            public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    productDetailsViewInterface.setProductDetails(response.body().getProductDetails());
                }
            }

            @Override
            public void onFailure(Call<ProductDetailsResponse> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }
}
