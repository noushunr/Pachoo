package com.presentation.app.dealsnest.ui.write_review;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.ReviewGet;

public interface WriteReviewViewInterface extends CommonViewInterface {

    void onFailedToSubmitReview(String message);

    void onReviewSubmitSuccess(String message);

    void onSuccessReadReviews(ReviewGet data);
}
