package com.highstreets.user.ui.notification.notification_details;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.notification_merchant_view_model;

import java.util.List;

public interface NotificationDetailViewInterface extends CommonViewInterface {

    void onSuccess(List<notification_merchant_view_model> notificationModelList);

    void onFails(String message);
}
