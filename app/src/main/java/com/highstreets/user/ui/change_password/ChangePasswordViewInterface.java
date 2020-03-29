
package com.highstreets.user.ui.change_password;

import com.highstreets.user.common.CommonViewInterface;

public interface ChangePasswordViewInterface extends CommonViewInterface {

    void failedToChangePassword(String message);

    void onPasswordChangeSuccess(String message);

}

