package com.highstreets.user.ui.place_order;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.ui.address.add_address.model.AddressResponse;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.place_order.model.FinalBalanceItem;

public interface PlaceOrderViewInterface extends CommonViewInterface {
    void setAddressResponse(AddressResponse addressResponse);

    void setFinalBalance(FinalBalanceItem finalBalanceItem);

    void setPlaceOrderResponse(PostResponse postResponse);
}
