
package com.highstreets.user.ui.review_booking;


import com.highstreets.user.common.CommonViewInterface;

public interface ReviewBookingViewInterface extends CommonViewInterface {

    void onLoadingReviewBookingSuccess(String message);

    void onLoadingReviewBookingFailed(String message);
}

