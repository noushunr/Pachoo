package com.highstreets.user.ui.change_password;

import com.highstreets.user.common.CommonPresenterInterface;

public interface ChangePasswordPresenterInterface extends CommonPresenterInterface {

    void changePassword(String register_id, String confirm_password);
}
