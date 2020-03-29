package com.highstreets.user.ui.offer_details;

import com.highstreets.user.common.CommonPresenterInterface;

public interface OfferDetailPresenterInterface extends CommonPresenterInterface {

    void getOfferDetails(String merchant_id, String offer_id, String user_id, String latitude, String longitude);

    void getAllOfferDetails(String merchantId, String user_id, String latitude, String longitude);
}
