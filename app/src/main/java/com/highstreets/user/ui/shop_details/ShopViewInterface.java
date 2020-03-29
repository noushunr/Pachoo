package com.highstreets.user.ui.shop_details;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.ShopDetail;
import com.highstreets.user.models.shop_images;
import com.highstreets.user.models.shops_most_popular;

import java.util.List;

public interface ShopViewInterface extends CommonViewInterface {

    void onLoadingShopImages(List<shop_images> shopImages);

    void onLoadingShopImagesFailed(String message);

    void onLoadingShopDetails(String message);

    void onFailedToLoadShopDetails(String message);

    void onLoadingShopDetailsMostPopular(List<shops_most_popular> mostPopularList);

    void onFailedToLoadShopDetailsMostPopular(String message);

    void setShopDetail(ShopDetail shopDetail);

    void onFailedToAddFav(String message);

    void onSuccessToAddFav(String message);
}
