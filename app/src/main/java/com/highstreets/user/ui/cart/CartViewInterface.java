package com.highstreets.user.ui.cart;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.ui.cart.model.CartData;
import com.highstreets.user.ui.cart.model.DeleteCartItemResponse;

public interface CartViewInterface extends CommonViewInterface {
    void setCartData(CartData cartData);

    void deleteResponse(DeleteCartItemResponse deleteCartItemResponse);
}
