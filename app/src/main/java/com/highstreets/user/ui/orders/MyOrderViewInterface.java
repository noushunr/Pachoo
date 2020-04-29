package com.highstreets.user.ui.orders;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.ui.orders.model.Order;

import java.util.List;

public interface MyOrderViewInterface extends CommonViewInterface {

    void setOrderList(List<Order> orderList);
}
