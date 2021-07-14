package com.highstreets.user.ui.place_order;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.Result;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.cart.model.CartData;
import com.highstreets.user.ui.place_order.model.FinalBalanceItem;
import com.highstreets.user.ui.place_order.model.payment.MakePaymentResponse;

import java.util.List;

public interface PlaceOrderViewInterface extends CommonViewInterface {

    void setFinalBalance(FinalBalanceItem finalBalanceItem);

    void setPlaceOrderResponse(ProductResult postResponse);

    void setCartData(List<Success> cartData);

    void paymentSuccess(ProductResult makePaymentResponse);

    void paymentError(ProductResult makePaymentResponse);
}
