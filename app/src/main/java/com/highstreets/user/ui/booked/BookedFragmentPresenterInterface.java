package com.highstreets.user.ui.booked;

import com.highstreets.user.common.CommonPresenterInterface;

public interface BookedFragmentPresenterInterface extends CommonPresenterInterface {

    void getBookedOffers(String user_id);
}

