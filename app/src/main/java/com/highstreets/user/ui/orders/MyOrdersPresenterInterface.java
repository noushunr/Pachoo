package com.highstreets.user.ui.orders;

import com.highstreets.user.common.CommonPresenterInterface;

public interface MyOrdersPresenterInterface extends CommonPresenterInterface {

    void getOrders(String userId);

}
