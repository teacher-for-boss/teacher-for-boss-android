package com.company.teacherforboss.presentation.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.company.teacherforboss.R
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_POSTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_QUESTIONID
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject
import java.util.Random

class TFBFirebaseMessagingService: FirebaseMessagingService() {
    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "teacherForBoss"
        private const val NOTIFICATION_CHANNEL_NAME = "Notification"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION = "NotificationChannel"
        const val NOTIFICATION_ID="notificationId"
        const val INFO="INFO"

        const val NOTIFICATIONTYPE="notificationType"
        const val NOTIFICATIONLINKDATA="notificationLinkData"

        const val QUESTION="QUESTION"
        const val POST="POST"
        const val HOME="HOME"
        const val EXCHANGE="EXCHANGE"
    }

    override fun onNewToken(token: String) {
        Log.d("FCM Log", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM Log", "Message received from: ${remoteMessage.from}")

        var title=""
        var body=""
        remoteMessage.notification?.let {
            title=it.title!!
            body=it.body!!
            Log.d("FCM Log", "Notification Title: $title")
            Log.d("FCM Log", "Notification Body: $body")
        }
        remoteMessage.data.let { data->
            val fullType=data[NOTIFICATIONTYPE]?:""
            var type=fullType.split("_")[0]
            val notificationId = data[NOTIFICATION_ID]?.toLong() ?: -1L

            var dataIdName:String?=null
            var dataId:Long?=null

            when(type){
                QUESTION -> dataIdName=TEACHER_QUESTIONID
                POST-> dataIdName= BOSS_POSTID
            }

            Log.d("fcm log",remoteMessage.data.toString())
            dataIdName?.let {
                dataId=remoteMessage.data[NOTIFICATIONLINKDATA]?.let{
                    try{
                        val jsonObject=JSONObject(it)
                        jsonObject.getLong(dataIdName)
                    }catch (e:JSONException){null}
                }
            }

            Log.d("FCM Log", "Notification type: $type")
            Log.d("FCM Log", "Notification id: $dataId")
            showNotification(title,body,type,notificationId,dataId)
        }


    }

    private fun showNotification(title: String, body: String,type:String,notificationId:Long,dataId:Long?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        val notificationId=Random().nextInt()

        // 화면 전환 도착지
        val notificationNavigationType=NotificationNavigationType.from(type)
        val destinationActivity=notificationNavigationType?.destinationActivity

        val intent=Intent(this,destinationActivity).apply{
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra(NOTIFICATION_ID,notificationId)
            dataId?.let {
                when(type){
                    QUESTION->putExtra(TEACHER_QUESTIONID,it)
                    POST->putExtra(BOSS_POSTID,it)
                }
            }
        }

        val pendingIntent=PendingIntent.getActivity(
            this,0,intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.logo_teacherforboss)
            .setContentTitle(title)
            .setContentText(body)
            .setContentInfo(INFO)
            .setContentIntent(pendingIntent)
        notificationManager.notify(notificationId, notificationBuilder.build())
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