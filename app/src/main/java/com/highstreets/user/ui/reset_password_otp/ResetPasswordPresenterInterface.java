package com.highstreets.user.ui.reset_password_otp;

import com.highstreets.user.common.CommonPresenterInterface;

public interface ResetPasswordPresenterInterface extends CommonPresenterInterface {

    void verify_password(String reset_key, String register_id);

}
