package com.presentation.app.dealsnest.ui.reset_password_otp;

import com.presentation.app.dealsnest.common.CommonViewInterface;

public interface ResetPasswordViewInterface extends CommonViewInterface {
    void failedToReset(String message);

    void onResetSuccess(String message);
}
