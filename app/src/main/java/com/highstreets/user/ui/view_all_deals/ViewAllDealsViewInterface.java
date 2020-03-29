package com.highstreets.user.ui.view_all_deals;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.ViewAllDeals;

import java.util.List;

public interface ViewAllDealsViewInterface extends CommonViewInterface {

    void onLoadingAllDealsSuccess(List<ViewAllDeals> viewAllDealsList);

    void onLoadingAllDealsFailed(String message);
}
