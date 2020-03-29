package com.highstreets.user.ui.coupons;

import com.highstreets.user.common.CommonPresenterInterface;

public interface CouponsPresenterInterface extends CommonPresenterInterface {

    void getCoupons(String city, String userId);

    void addFavoriteCoupon(String userId, String couponId);

    void couponBooking(String userId, String couponId);
}
