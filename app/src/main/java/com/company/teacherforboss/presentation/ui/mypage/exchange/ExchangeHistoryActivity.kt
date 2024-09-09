package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityExchangeHistoryBinding
import com.company.teacherforboss.presentation.ui.notification.NotificationViewModel
import com.company.teacherforboss.presentation.ui.notification.TFBFirebaseMessagingService.Companion.NOTIFICATION_ID
import com.company.teacherforboss.util.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeHistoryActivity : BindingActivity<ActivityExchangeHistoryBinding>(R.layout.activity_exchange_history) {
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


