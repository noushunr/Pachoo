package com.highstreets.user.ui.profile;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.ProfileData;

public interface ProfileViewInterface extends CommonViewInterface {

    void failedToUpdateProfile(String message);

    void onProfileUpdateSuccess(String message, String image);

    void onLoadingProfileSuccess(ProfileData profile);

    void onFailingToLoadProfile(String message);
}
