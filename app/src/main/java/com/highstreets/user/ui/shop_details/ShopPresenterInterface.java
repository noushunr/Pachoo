package com.highstreets.user.ui.shop_details;

import com.highstreets.user.common.CommonPresenterInterface;

public interface ShopPresenterInterface extends CommonPresenterInterface {

    void getShopDetails(String merchant_id, String user_id, String longitude, String latitude, String city_name);

    void addFavouriteShop(String merchant_id, String user_id, String fav_status);
}
