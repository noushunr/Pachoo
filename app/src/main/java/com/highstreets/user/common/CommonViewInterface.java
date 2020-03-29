package com.highstreets.user.common;

public interface CommonViewInterface {

    void onResponseFailed(String message);

    void onServerError(String message);

    void dismissProgressIndicator();

    void noInternet();

    void showProgressIndicator();

}
