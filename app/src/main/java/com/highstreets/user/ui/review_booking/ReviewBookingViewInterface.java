
package com.highstreets.user.ui.review_booking;


import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.ui.cart.model.CartData;

public interface ReviewBookingViewInterface extends CommonViewInterface {

    void onLoadingReviewBookingSuccess(String message);

    void onLoadingReviewBookingFailed(String message);

    void setCartData(CartData cartData);
}

