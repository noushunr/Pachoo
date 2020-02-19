package com.presentation.app.dealsnest.ui.view_all_recently_booked;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.ViewAllRecentlyBooked;

import java.util.List;

public interface ViewAllRecentlyBookedViewInterFace extends CommonViewInterface {

    void onLoadingAllRecentlyBookedSuccess(List<ViewAllRecentlyBooked> viewAllRecentlyBookedList);

    void onLoadingAllRecentlyBookedFailed(String message);

}
