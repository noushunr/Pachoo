package com.highstreets.user.ui.main.home;

import com.highstreets.user.common.CommonPresenterInterface;

public interface HomePresenterInterface extends CommonPresenterInterface {

    void getHomeDetails(String latitude,String longitude);
}
