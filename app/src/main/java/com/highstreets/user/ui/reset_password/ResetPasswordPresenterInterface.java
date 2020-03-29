package com.highstreets.user.ui.reset_password;

import com.highstreets.user.common.CommonPresenterInterface;

public interface ResetPasswordPresenterInterface extends CommonPresenterInterface {
    void resetPassword(String register_id, String new_password);
}
