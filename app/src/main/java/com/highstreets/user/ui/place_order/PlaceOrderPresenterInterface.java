package com.highstreets.user.ui.place_order;

import com.highstreets.user.common.CommonPresenterInterface;
//import com.stripe.Stripe;

public interface PlaceOrderPresenterInterface extends CommonPresenterInterface {

    void getFinalBalance(String userId,
                         String addressId);

    void getCartProducts(String userId);

    void placeOrder(String userId,
                    String addressId,
                    String paymentMethod);

    void makePayment(
            String userId,
            String orderId,
            String status
                     );
}
