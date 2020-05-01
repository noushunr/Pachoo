package com.highstreets.user.ui.orders.order_details;

import com.highstreets.user.common.CommonPresenterInterface;

public interface OrderDetailsPresenterInterface extends CommonPresenterInterface {
    void getOrder(String userId, String orderId);
}
