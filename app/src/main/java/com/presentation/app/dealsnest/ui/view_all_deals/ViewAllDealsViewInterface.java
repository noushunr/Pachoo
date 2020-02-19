package com.presentation.app.dealsnest.ui.view_all_deals;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.ViewAllDeals;

import java.util.List;

public interface ViewAllDealsViewInterface extends CommonViewInterface {

    void onLoadingAllDealsSuccess(List<ViewAllDeals> viewAllDealsList);

    void onLoadingAllDealsFailed(String message);
}
