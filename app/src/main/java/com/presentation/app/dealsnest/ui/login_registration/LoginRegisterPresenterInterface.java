package com.presentation.app.dealsnest.ui.login_registration;

import com.presentation.app.dealsnest.common.CommonPresenterInterface;


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
