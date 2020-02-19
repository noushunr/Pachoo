package com.presentation.app.dealsnest.ui.profile;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.ProfileData;

public interface ProfileActivityViewInterface extends CommonViewInterface {

    void failedToUpdateProfile(String message);

    void onProfileUpdateSuccess(String message, String image);

    void onLoadingProfileSuccess(ProfileData profile);

    void onFailingToLoadProfile(String message);
}
