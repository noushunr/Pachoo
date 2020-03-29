
package com.highstreets.user.ui.notification;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.notification_model;

import java.util.List;

public interface NotificationViewInterface extends CommonViewInterface {

    void failedToNotify(String message);

    void onNotificationSuccess(List<notification_model> notificationModelList);
}

