
package com.presentation.app.dealsnest.ui.reset_password;


import com.presentation.app.dealsnest.common.CommonViewInterface;

public interface ResetPasswordViewInterface extends CommonViewInterface {
    void failedToResetNewPassword(String message);

    void onResetPasswordSuccess(String message);
}

