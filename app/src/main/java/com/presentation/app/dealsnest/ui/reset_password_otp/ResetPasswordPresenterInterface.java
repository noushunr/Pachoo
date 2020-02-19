package com.presentation.app.dealsnest.ui.reset_password_otp;

import com.presentation.app.dealsnest.common.CommonPresenterInterface;

public interface ResetPasswordPresenterInterface extends CommonPresenterInterface {

    void verify_password(String reset_key, String register_id);

}
