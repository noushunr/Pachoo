
package com.presentation.app.dealsnest.ui.review_booking;


import com.presentation.app.dealsnest.common.CommonViewInterface;

public interface ReviewBookingViewInterface extends CommonViewInterface {

    void onLoadingReviewBookingSuccess(String message);

    void onLoadingReviewBookingFailed(String message);
}

