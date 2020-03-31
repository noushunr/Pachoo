package com.highstreets.user.ui.main;

import com.highstreets.user.common.CommonPresenterInterface;

public interface HomeMainPresenterInterface extends CommonPresenterInterface {

    void addTokens(String userId,
                   String token,
                   String type);
}
