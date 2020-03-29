
package com.highstreets.user.ui.reset_password;


import com.highstreets.user.common.CommonViewInterface;

public interface ResetPasswordViewInterface extends CommonViewInterface {
    void failedToResetNewPassword(String message);

    void onResetPasswordSuccess(String message);
}

