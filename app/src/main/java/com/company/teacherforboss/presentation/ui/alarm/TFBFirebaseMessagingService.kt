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
        private const val NOTIFICATION_CHANNEL_ID = "teacherForBoss"
        private const val NOTIFICATION_CHANNEL_NAME = "Notification"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION = "NotificationChannel"
        const val INFO="INFO"
    }

    override fun onNewToken(token: String) {
        Log.d("FCM Log", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM Log", "Message received from: ${remoteMessage.from}")
        Log.d("fcm test",remoteMessage.toString())
        remoteMessage.notification!!.body?.let {
            val title=remoteMessage.notification!!.title.toString()
            val body = remoteMessage.notification!!.body.toString()
            Log.d("fcm test",title)
            Log.d("fcm test",body)
            showNotification(title,body)
        }
    }

    private fun showNotification(title: String, content: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.logo_teacherforboss)
            .setContentTitle(title)
            .setContentText(content)
            .setContentInfo(INFO)
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