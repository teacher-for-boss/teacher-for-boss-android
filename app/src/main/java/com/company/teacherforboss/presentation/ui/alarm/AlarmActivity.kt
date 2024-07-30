package com.company.teacherforboss.presentation.ui.alarm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.company.teacherforboss.R
import com.google.firebase.messaging.FirebaseMessaging
class AlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener { task ->
                if (!task.isSuccessful()) {
                    Log.w("FCM Log", "Fetching FCM registration token failed", task.getException())
                    return@addOnCompleteListener
                }
                val token: String = task.getResult()
                Log.d("FCM Log", "Current token: $token")
            }
    }
}