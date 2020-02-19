package com.presentation.app.dealsnest.ui.reset_password;

import com.presentation.app.dealsnest.common.CommonPresenterInterface;

public interface ResetPasswordPresenterInterface extends CommonPresenterInterface {
    void resetPassword(String register_id, String new_password);
}
