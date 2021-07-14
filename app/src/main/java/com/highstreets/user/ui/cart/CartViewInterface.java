package com.highstreets.user.ui.cart;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.cart.model.CartData;
import com.highstreets.user.ui.cart.model.DeleteCartItemResponse;

import java.util.List;

public interface CartViewInterface extends CommonViewInterface {
    void setCartData(List<Success> cartData);

    void deleteResponse(DeleteCartItemResponse deleteCartItemResponse);

    void setCartFailed();
}
