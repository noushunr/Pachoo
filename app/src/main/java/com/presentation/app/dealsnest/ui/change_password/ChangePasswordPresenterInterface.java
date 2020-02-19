package com.presentation.app.dealsnest.ui.change_password;

import com.presentation.app.dealsnest.common.CommonPresenterInterface;

public interface ChangePasswordPresenterInterface extends CommonPresenterInterface {

    void changePassword(String register_id, String confirm_password);
}
