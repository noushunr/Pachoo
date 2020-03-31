package com.highstreets.user.ui.profile;

import com.highstreets.user.common.CommonPresenterInterface;

public interface ProfilePresenterInterface extends CommonPresenterInterface {

    void updateProfile(String register_id,
                       String firstname,
                       String lastname,
                       String email_id,
                       String mobile,
                       String profile_pic);

    void loadProfileDetails(String register_id);
}
