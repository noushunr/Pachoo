package com.highstreets.user.ui.login_registration;

import com.highstreets.user.common.CommonPresenterInterface;


public interface LoginRegisterPresenterInterface extends CommonPresenterInterface {
    void userLogin(String email, String password);

    void userRegister(String firstname,
                      String lastname,
                      String email_id,
                      String password,
                      String confirm_password,
                      String mobile,
                      String gender);

    void userRegisterSocial(String type,
                            String profileId,
                            String name,
                            String email,
                            String profileImage);

}
