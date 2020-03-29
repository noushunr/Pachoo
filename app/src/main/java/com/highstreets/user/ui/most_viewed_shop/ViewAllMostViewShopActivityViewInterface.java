package com.highstreets.user.ui.most_viewed_shop;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.MostViewedShop;

import java.util.List;

public interface ViewAllMostViewShopActivityViewInterface extends CommonViewInterface {

    void onLoadingAllShopsSuccess(List<MostViewedShop> mostViewedShopList);

    void onLoadingAllShopsFailed(String message);
}
