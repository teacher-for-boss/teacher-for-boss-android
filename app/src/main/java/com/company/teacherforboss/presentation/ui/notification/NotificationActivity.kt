package com.company.teacherforboss.presentation.ui.notification

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityNotificationBinding
import com.company.teacherforboss.presentation.ui.notification.TFBFirebaseMessagingService.Companion.NOTIFICATION_ID
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.view.UiState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NotificationActivity : BindingActivity<ActivityNotificationBinding>(R.layout.activity_notification) {
    private val viewModel by viewModels<NotificationViewModel>()
    private lateinit var notificationAdapter:NotificationAdapter

    private var backPressedOnce = false
    private val exitHandler = Handler(Looper.getMainLooper())
    private val resetBackPressed = Runnable { backPressedOnce = false }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setNotificationView()
        setFirebaseMessaging()
        addListeners()
    }

    fun setNotificationView(){
        notificationAdapter = NotificationAdapter(this, mutableListOf())

        viewModel.getNotifications()

        viewModel.notificationState.flowWithLifecycle(this.lifecycle)
            .onEach { notificationState->
                when(notificationState){
                    is UiState.Success->{
                        val notificationList = notificationState.data.notificationList

                        if(notificationList.isEmpty()) {
                            binding.btnNotificationMore.visibility = View.GONE
                            binding.tvNotificationInfo.visibility = View.VISIBLE
                        } else {
                            val previousLastNotificationId = viewModel.getLastPostId()
                            val lastNotificationId = notificationList.get(notificationList.lastIndex).notificationId
                            viewModel.setLastPostId(lastNotificationId)

                            if(previousLastNotificationId.toInt() == 0) notificationAdapter.updateData(notificationList)
                            else notificationAdapter.addMoreData(notificationList)

                            if(notificationState.data.hasNext) {
                                binding.btnNotificationMore.visibility = View.VISIBLE
                                binding.tvNotificationInfo.visibility = View.GONE
                            }
                            else  {
                                binding.btnNotificationMore.visibility = View.GONE
                                binding.tvNotificationInfo.visibility = View.VISIBLE
                            }
                        }
                    }
                    else-> Unit
                }
            }.launchIn(this.lifecycleScope)

        with(binding.rvNotification){
            adapter=notificationAdapter
            layoutManager=LinearLayoutManager(this@NotificationActivity)
        }
        onBackBtnPressed()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    fun readNotification(){
        val notifiationId=intent.getLongExtra(NOTIFICATION_ID,-1L)
        viewModel.readNotification(notifiationId)
    }

    private fun addListeners() {
        binding.btnNotificationMore.setOnClickListener {
            viewModel.getNotifications()
        }
    }

    fun updateNotificationState(){
        viewModel.readNotificationState.flowWithLifecycle(this.lifecycle).onEach { readState->
            when(readState){
                is UiState.Success-> "" //TODO: 해당 notification entity의 read와 updatedAt 변경
                else->Unit
            }
        }.launchIn(this.lifecycleScope)
    }

    fun setFirebaseMessaging(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(alarm_tag, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d(alarm_tag,token)
        })
    }

    fun onBackBtnPressed(){binding.includeNotificationTopAppBar.backBtn.setOnClickListener {finish()}}

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    companion object{
        const val alarm_tag="ALARM"
    }
}