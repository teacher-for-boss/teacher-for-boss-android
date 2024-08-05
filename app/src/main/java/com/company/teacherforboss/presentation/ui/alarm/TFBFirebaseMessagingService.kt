package com.company.teacherforboss.presentation.ui.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.company.teacherforboss.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Random

class TFBFirebaseMessagingService: FirebaseMessagingService() {
    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "tfb"
        private const val NOTIFICATION_CHANNEL_NAME = "Notification"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION = "notification channel"
    }

    override fun onNewToken(token: String) {
        Log.d("FCM Log", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            val content = remoteMessage.data["content"]
            content?.let { showNotification(it) }
        }
    }

    private fun showNotification(content: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("A new post you might be interested in.")
            .setContentText(content)
            .setContentInfo("Info")
        notificationManager.notify(Random().nextInt(), notificationBuilder.build())
    }

    private fun createNotificationChannel(manager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = NOTIFICATION_CHANNEL_DESCRIPTION
            }
            manager.createNotificationChannel(notificationChannel)
        }
    }



}