package com.highstreets.user.ui.auth.login_registration;

import com.highstreets.user.common.CommonViewInterface;

public interface LoginRegisterViewInterface extends CommonViewInterface {
    void failedToSignIn(String message);
    void onSighInSuccess(String message);
}
