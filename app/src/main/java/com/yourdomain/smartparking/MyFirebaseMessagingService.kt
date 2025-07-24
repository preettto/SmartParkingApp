package com.yourdomain.smartparking

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yourdomain.smartparking.utils.NotificationUtils

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title ?: "Smart Parking"
        val message = remoteMessage.notification?.body ?: "You have a new notification."
        NotificationUtils.showNotification(applicationContext, title, message)
    }
} 