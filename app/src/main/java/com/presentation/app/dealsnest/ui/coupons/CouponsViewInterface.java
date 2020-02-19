package com.presentation.app.dealsnest.ui.coupons;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.Coupon;
import com.presentation.app.dealsnest.models.FavoriteCouponBooking;

import java.util.List;

public interface CouponsViewInterface extends CommonViewInterface {
    void setCoupons(List<Coupon> couponList);

    void onFavoritesAdded(FavoriteCouponBooking favoriteCouponBooking);

    void onBooked(FavoriteCouponBooking favoriteCouponBooking);
}
