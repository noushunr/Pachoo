package com.highstreets.user.ui.write_review;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.ReviewGet;

public interface WriteReviewViewInterface extends CommonViewInterface {

    void onFailedToSubmitReview(String message);

    void onReviewSubmitSuccess(String message);

    void onSuccessReadReviews(ReviewGet data);
}
