package com.presentation.app.dealsnest.ui.profile;

import com.presentation.app.dealsnest.common.CommonPresenterInterface;

public interface ProfileActivityPresenterInterface extends CommonPresenterInterface {

    void updateProfile(String register_id,
                       String firstname,
                       String lastname,
                       String email_id,
                       String mobile,
                       String profile_pic);

    void loadProfileDetails(String register_id);
}
