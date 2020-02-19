
package com.presentation.app.dealsnest.ui.notification;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.notification_model;

import java.util.List;

public interface NotificationViewInterface extends CommonViewInterface {

    void failedToNotify(String message);

    void onNotificationSuccess(List<notification_model> notificationModelList);
}

