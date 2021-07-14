package com.highstreets.user.ui.profile;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.ProfileData;

public interface ProfileViewInterface extends CommonViewInterface {

    void failedToUpdateProfile(String message);

    void onProfileUpdateSuccess(String message, String image);

    void onLoadingProfileSuccess(ProductResult profile);

    void onFailingToLoadProfile(String message);
}
