package com.highstreets.user.ui.auth.login_registration;

import com.highstreets.user.common.CommonPresenterInterface;


public interface LoginRegisterPresenterInterface extends CommonPresenterInterface {
    void userLogin(String email, String password,String token);
    void otpVerification(String mobile,String otp, String sessionId);
    void userRegister(String firstname,
                      String email_id,
                      String password,
                      String confirm_password,
                      String gender,
                      String mobile,
                      String token);

    void userRegisterSocial(String type,
                            String profileId,
                            String name,
                            String email,
                            String profileImage);

}
