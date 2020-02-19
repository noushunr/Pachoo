
package com.presentation.app.dealsnest.ui.change_password;

import com.presentation.app.dealsnest.common.CommonViewInterface;

public interface ChangePasswordViewInterface extends CommonViewInterface {

    void failedToChangePassword(String message);

    void onPasswordChangeSuccess(String message);

}

