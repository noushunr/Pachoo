package com.presentation.app.dealsnest.ui.shop_details;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.ShopDetail;
import com.presentation.app.dealsnest.models.shop_images;
import com.presentation.app.dealsnest.models.shops_most_popular;

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
