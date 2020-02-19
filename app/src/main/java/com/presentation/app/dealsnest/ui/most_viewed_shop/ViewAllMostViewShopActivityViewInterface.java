package com.presentation.app.dealsnest.ui.most_viewed_shop;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.MostViewedShop;

import java.util.List;

public interface ViewAllMostViewShopActivityViewInterface extends CommonViewInterface {

    void onLoadingAllShopsSuccess(List<MostViewedShop> mostViewedShopList);

    void onLoadingAllShopsFailed(String message);
}
