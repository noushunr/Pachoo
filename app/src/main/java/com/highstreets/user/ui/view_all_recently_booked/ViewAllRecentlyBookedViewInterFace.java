package com.highstreets.user.ui.view_all_recently_booked;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.ViewAllRecentlyBooked;

import java.util.List;

public interface ViewAllRecentlyBookedViewInterFace extends CommonViewInterface {

    void onLoadingAllRecentlyBookedSuccess(List<ViewAllRecentlyBooked> viewAllRecentlyBookedList);

    void onLoadingAllRecentlyBookedFailed(String message);

}
