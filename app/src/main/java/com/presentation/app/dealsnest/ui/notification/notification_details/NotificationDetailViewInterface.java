package com.presentation.app.dealsnest.ui.notification.notification_details;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.notification_merchant_view_model;

import java.util.List;

public interface NotificationDetailViewInterface extends CommonViewInterface {

    void onSuccess(List<notification_merchant_view_model> notificationModelList);

    void onFails(String message);
}
