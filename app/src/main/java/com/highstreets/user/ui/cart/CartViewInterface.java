package com.highstreets.user.ui.cart;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.ui.cart.model.CartData;

public interface CartViewInterface extends CommonViewInterface {
    void setCartData(CartData cartData);
}
