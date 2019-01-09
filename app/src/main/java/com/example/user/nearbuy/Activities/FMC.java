package com.example.user.nearbuy.Activities;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FMC extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {
            // do with Notification payload...
            // remoteMessage.getNotification().getBody()
        }

        if (remoteMessage.getData().size() > 0) {
            // do with Data payload...
            // remoteMessage.getData()
        }
    }
}