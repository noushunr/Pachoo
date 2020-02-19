package com.presentation.app.dealsnest.ui.booked;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.BookedOffers;

import java.util.List;

public interface BookedFragmentViewInterface extends CommonViewInterface {

    void onLoadingBookedOffersSuccess(List<BookedOffers> booked_modelList);

    void onLoadingBookedOffersFailed(String message);

}
