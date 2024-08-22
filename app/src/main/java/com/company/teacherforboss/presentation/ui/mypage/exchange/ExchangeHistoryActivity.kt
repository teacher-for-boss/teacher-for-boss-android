package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.databinding.ActivityExchangeHistoryBinding
import com.company.teacherforboss.presentation.ui.notification.NotificationViewModel
import com.company.teacherforboss.presentation.ui.notification.TFBFirebaseMessagingService.Companion.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExchangeHistoryBinding
    private val notificationViewModel by viewModels<NotificationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val items = listOf(
            ExchangeHistoryItem("exchange",800,"24.05.05 12:15"),
            ExchangeHistoryItem("refund",800,"24.05.05 12:15"),
            ExchangeHistoryItem("exchange",300,"24.05.05 12:15"),
            ExchangeHistoryItem("refund",300,"24.05.05 12:15"),
            ExchangeHistoryItem("exchange",400,"24.05.05 12:15"),
            )
        val adapter = rvAdapterExchangeHistory(this, items)
        binding.rvExchangeHistory.adapter = adapter
        binding.rvExchangeHistory.layoutManager = LinearLayoutManager(this)

        readNotification()
        onBackBtnPressed()
    }

    fun readNotification(){
        val notifiationId=intent.getLongExtra(NOTIFICATION_ID,-1L)
        notificationViewModel.readNotification(notifiationId)
    }

    fun onBackBtnPressed(){
        binding.includeExchangeHistoryTopAppBar.backBtn.setOnClickListener{finish()}
    }
}


