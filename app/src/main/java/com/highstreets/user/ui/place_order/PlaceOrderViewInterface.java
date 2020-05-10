package com.highstreets.user.ui.place_order;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.cart.model.CartData;
import com.highstreets.user.ui.place_order.model.FinalBalanceItem;
import com.highstreets.user.ui.place_order.model.payment.MakePaymentResponse;

public interface PlaceOrderViewInterface extends CommonViewInterface {

    void setFinalBalance(FinalBalanceItem finalBalanceItem);

    void setPlaceOrderResponse(PostResponse postResponse);

    void setCartData(CartData cartData);

    void paymentSuccess(MakePaymentResponse makePaymentResponse);

    void paymentError(MakePaymentResponse makePaymentResponse);
}
