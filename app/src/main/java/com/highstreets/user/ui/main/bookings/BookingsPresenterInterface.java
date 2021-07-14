package com.highstreets.user.ui.main.bookings;

import com.highstreets.user.common.CommonPresenterInterface;

public interface BookingsPresenterInterface extends CommonPresenterInterface {

    void getBookedOffers(String user_id);
    void deleteBookings(String userId, String bookingId);
}

