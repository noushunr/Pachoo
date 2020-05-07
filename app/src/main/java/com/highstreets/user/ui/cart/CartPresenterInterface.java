package com.highstreets.user.ui.cart;

import com.highstreets.user.common.CommonPresenterInterface;

public interface CartPresenterInterface extends CommonPresenterInterface {

    void getCartProducts(String userId);

    void deleteCart(String userId, String cartId);

}
