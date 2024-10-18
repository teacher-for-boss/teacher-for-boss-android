package com.company.teacherforboss.presentation.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.company.teacherforboss.R
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_POSTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.FRAGMENT_DESTINATION
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_QUESTIONID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_TALK
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.base.LocalDataSource.Companion.FCM_TOKEN
import com.company.teacherforboss.util.base.LocalDataSource.Companion.INFO_NULL
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject
import java.util.Random
import javax.inject.Inject

@AndroidEntryPoint
class TFBFirebaseMessagingService: FirebaseMessagingService() {
    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "teacherForBoss"
        private const val NOTIFICATION_CHANNEL_NAME = "Notification"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION = "NotificationChannel"
        const val NOTIFICATION_ID="notificationId"
        const val INFO="INFO"

        const val NOTIFICATION="notification"
        const val NOTIFICATIONTYPE="notificationType"
        const val NOTIFICATIONLINKDATA="notificationLinkData"

        const val QUESTION="QUESTION"
        const val POST="POST"
        const val HOME="HOME"
        const val EXCHANGE="EXCHANGE"
        const val QUESTION_AUTO_DELETE="QUESTION_AUTO_DELETE"
    }
    @Inject
    lateinit var localDataSource: LocalDataSource

    override fun onNewToken(token: String) {
       localDataSource.saveUserInfo(FCM_TOKEN,token)
        Log.d("FCM Log", "Refreshed token: $token")
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM Log", "Message received from: ${remoteMessage.from}")

        var title=""
        var body=""

        remoteMessage.data.let { data ->
            val notificationDefault = data["default"]?.let { defaultData ->
                JSONObject(defaultData)
            }

            notificationDefault?.let {
                val notification = it.optJSONObject(NOTIFICATION)
                title = notification?.optString("title", "No Title") ?: "No Title"
                body = notification?.optString("body", "No Body") ?: "No Body"

                Log.d("fcm log",notificationDefault.toString())
                Log.d("FCM Log", "Notification Title: $title")
                Log.d("FCM Log", "Notification Body: $body")

                val notificationData=it.optJSONObject("data")
                val notificationType=notificationData?.optString(NOTIFICATIONTYPE)?:""

                var type=""
                if(notificationType==QUESTION_AUTO_DELETE) type=notificationType
                else type=notificationType.split("_")[0]

                var dataIdName: String? = null
                var dataId: Long? = null

                when(type){
                    QUESTION -> dataIdName=TEACHER_QUESTIONID
                    POST-> dataIdName= BOSS_POSTID
                }

                dataIdName?.let{
                    val notificationLinkData=notificationData.optJSONObject(NOTIFICATIONLINKDATA)
                    dataId=notificationLinkData?.optString(dataIdName)?.toLong()

                }
                Log.d("FCM Log", "Notification type: $type")
                Log.d("FCM Log", "Notification id: $dataId")
                Log.d("FCM Log", "Notification data id name: $dataIdName")
                showNotification(title,body,type,dataId)
            }
        }
    }

    private fun showNotification(title: String, body: String,type:String,dataId:Long?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        val notificationId=Random().nextInt()

        val uniqueRequestCode = Random().nextInt() // 각 알림에 대해 고유한 요청 코드 생성

        // 화면 전환 도착지
        val notificationNavigationType=NotificationNavigationType.from(type)
        val destinationActivity=notificationNavigationType?.destinationActivity
        Log.d("fcm log destination activity",destinationActivity.toString())

        val intent=Intent(this,destinationActivity).apply{
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            putExtra(NOTIFICATION_ID,notificationId)
            if (type== QUESTION_AUTO_DELETE) putExtra(FRAGMENT_DESTINATION, TEACHER_TALK)
            Log.d("fcm log data id",dataId.toString())
            dataId?.let {
                when(type){
                    QUESTION->putExtra(TEACHER_QUESTIONID,it)
                    POST->putExtra(BOSS_POSTID,it)
                }
            }
        }

        val pendingIntent=PendingIntent.getActivity(
            this,uniqueRequestCode,intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.logo_teacherforboss)

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.teacherforboss_small_icon)
//            .setLargeIcon(largeIcon)
            .setContentTitle(title)
            .setContentText(body)
            .setContentInfo(INFO)
            .setColor(ContextCompat.getColor(this, R.color.Primary01))
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