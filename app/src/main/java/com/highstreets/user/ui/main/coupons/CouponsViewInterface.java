package com.highstreets.user.ui.main.coupons;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.Coupon;
import com.highstreets.user.models.FavoriteCouponBooking;

import java.util.List;

public interface CouponsViewInterface extends CommonViewInterface {
    void setCoupons(List<Coupon> couponList);

    void onFavoritesAdded(FavoriteCouponBooking favoriteCouponBooking);

    void onBooked(FavoriteCouponBooking favoriteCouponBooking);
}
