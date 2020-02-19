package com.presentation.app.dealsnest.ui.coupons;

import com.presentation.app.dealsnest.common.CommonPresenterInterface;

public interface CouponsPresenterInterface extends CommonPresenterInterface {

    void getCoupons(String city, String userId);

    void addFavoriteCoupon(String userId, String couponId);

    void couponBooking(String userId, String couponId);
}
