package com.highstreets.user.ui.cart.product_details;

import com.highstreets.user.common.CommonPresenterInterface;

public interface ProductDetailsPresenterInterface extends CommonPresenterInterface {
    void getProduct(String productId);
}
