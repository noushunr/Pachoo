package com.highstreets.user.ui.main.bookings;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.BookedOffers;
import com.highstreets.user.ui.cart.model.DeleteCartItemResponse;

import java.util.List;

public interface BookingsViewInterface extends CommonViewInterface {

    void onLoadingBookedOffersSuccess(List<BookedOffers> booked_modelList);

    void onLoadingBookedOffersFailed(String message);

    void deleteResponse(DeleteCartItemResponse deleteCartItemResponse);
}
