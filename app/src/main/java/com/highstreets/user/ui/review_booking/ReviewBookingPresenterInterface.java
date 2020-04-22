package com.highstreets.user.ui.review_booking;

import com.highstreets.user.common.CommonPresenterInterface;

import java.util.ArrayList;

public interface ReviewBookingPresenterInterface extends CommonPresenterInterface {

    void getReviewBooking(String user_id,
                          String merchant_id,
                          ArrayList<String> offer_id,
                          ArrayList<String> price,
                          ArrayList<String> quantity);

    void getCartProducts(String userId);
}
