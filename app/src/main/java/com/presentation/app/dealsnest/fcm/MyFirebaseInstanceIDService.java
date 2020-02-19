package com.presentation.app.dealsnest.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        GlobalPreferManager.setString(GlobalPreferManager.Keys.TOKEN, token);
    }
}