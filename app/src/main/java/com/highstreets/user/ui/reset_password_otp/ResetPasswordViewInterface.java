package com.highstreets.user.ui.reset_password_otp;

import com.highstreets.user.common.CommonViewInterface;

public interface ResetPasswordViewInterface extends CommonViewInterface {
    void failedToReset(String message);

    void onResetSuccess(String message);
}
