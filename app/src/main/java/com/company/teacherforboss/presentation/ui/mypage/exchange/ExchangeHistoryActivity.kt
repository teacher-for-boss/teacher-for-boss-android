package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityExchangeHistoryBinding
import com.company.teacherforboss.presentation.ui.notification.NotificationViewModel
import com.company.teacherforboss.presentation.ui.notification.TFBFirebaseMessagingService.Companion.NOTIFICATION_ID
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ExchangeHistoryActivity : BindingActivity<ActivityExchangeHistoryBinding>(R.layout.activity_exchange_history) {
    private val notificationViewModel by viewModels<NotificationViewModel>()
    private val exchangeViewModel by viewModels<ExchangeViewModel>()
    private lateinit var exchangeListAdapter: rvAdapterExchangeHistory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exchangeViewModel.getExchangeList()
        initAdapter()
        readNotification()
        onBackBtnPressed()
        collectData()
    }

    private fun collectData() {
        exchangeViewModel.getExchangeListUiState.flowWithLifecycle(this.lifecycle)
            .onEach { exchangeListState ->
                when(exchangeListState) {
                    is UiState.Success -> {
                        val exchangeList = exchangeListState.data.exchangeList

                        if(exchangeList.isEmpty()) {

                        }
                        else {
                            exchangeListAdapter.updateData(exchangeList)
                        }
                    }
                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)
    }

    private fun initAdapter() {
        exchangeListAdapter = rvAdapterExchangeHistory(this, emptyList())
        binding.rvExchangeHistory.adapter = exchangeListAdapter
        binding.rvExchangeHistory.layoutManager = LinearLayoutManager(this)
    }

    fun readNotification(){
        val notificationId=intent.getLongExtra(NOTIFICATION_ID,-1L)
        notificationViewModel.readNotification(notificationId)
    }

    fun onBackBtnPressed(){
        binding.includeExchangeHistoryTopAppBar.backBtn.setOnClickListener{finish()}
    }
}


