
package com.presentation.app.dealsnest.ui.forgot_password;

import com.presentation.app.dealsnest.common.CommonViewInterface;

public interface ForgotPasswordViewInterface extends CommonViewInterface {

    void failedToSignIn(String message);

    void onResetLinkSent(String id, String message);
}

