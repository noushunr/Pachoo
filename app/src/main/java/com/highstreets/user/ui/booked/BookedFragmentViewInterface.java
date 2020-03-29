package com.highstreets.user.ui.booked;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.BookedOffers;

import java.util.List;

public interface BookedFragmentViewInterface extends CommonViewInterface {

    void onLoadingBookedOffersSuccess(List<BookedOffers> booked_modelList);

    void onLoadingBookedOffersFailed(String message);

}
