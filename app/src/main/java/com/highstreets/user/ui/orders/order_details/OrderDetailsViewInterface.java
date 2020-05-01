package com.highstreets.user.ui.orders.order_details;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.ui.orders.order_details.model.OrderDetailsResponse;

public interface OrderDetailsViewInterface extends CommonViewInterface {
    void setOrderDetails(OrderDetailsResponse orderDetailsResponse);
}
