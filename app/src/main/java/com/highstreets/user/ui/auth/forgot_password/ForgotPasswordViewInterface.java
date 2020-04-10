
package com.highstreets.user.ui.auth.forgot_password;

import com.highstreets.user.common.CommonViewInterface;

public interface ForgotPasswordViewInterface extends CommonViewInterface {

    void failedToSignIn(String message);

    void onResetLinkSent(String id, String message);
}

