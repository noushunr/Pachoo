
package com.highstreets.user.ui.auth.forgot_password;

import com.highstreets.user.common.CommonPresenterInterface;

public interface ForgotPasswordPresenterInterface extends CommonPresenterInterface {

    void forgotPassword(String email);
}

