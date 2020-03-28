package com.presentation.app.dealsnest.ui.login_registration;

import com.presentation.app.dealsnest.common.CommonViewInterface;

public interface LoginRegisterViewInterface extends CommonViewInterface {
    void failedToSignIn(String message);
    void onSighInSuccess(String message);
}
