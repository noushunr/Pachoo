package com.highstreets.user.ui.write_review;

import com.highstreets.user.common.CommonPresenterInterface;

public interface WriteReviewPresenterInterface extends CommonPresenterInterface {

    void write_review(String user_id, String merchant_id, String name, String review, String rating);

    void write_offer_review(String user_id, String merchant_id, String offer_id, String name, String review, String rating);

    void read_reviews(String user_id, String merchant_id, String section);
}
