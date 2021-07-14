package com.highstreets.user.ui.product;

import com.highstreets.user.common.CommonPresenterInterface;
import com.highstreets.user.models.Offer;

import java.util.ArrayList;

public interface ShopProductsPresenterInterface extends CommonPresenterInterface {

    void getOfferDetails(String merchant_id, String offer_id, String user_id, String latitude, String longitude);

    void getAllProducts(String shopId,String categoryId);

    void addToCart(String shopId, String productId, String quantity);

    void clearCart(String userId);
}
