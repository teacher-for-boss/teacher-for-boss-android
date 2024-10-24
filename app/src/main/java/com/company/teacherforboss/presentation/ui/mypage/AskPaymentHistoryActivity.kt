package com.company.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityAskPaymentHistoryBinding
import com.company.teacherforboss.presentation.ui.mypage.exchange.AskPaymentHistoryItem
import com.company.teacherforboss.presentation.ui.mypage.rvAdapterAskPaymentHistory
import com.company.teacherforboss.util.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AskPaymentHistoryActivity : BindingActivity<ActivityAskPaymentHistoryBinding>(R.layout.activity_ask_payment_history) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskPaymentHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val items = listOf(
            AskPaymentHistoryItem("pay",10,"24.05.05 12:15"),
            AskPaymentHistoryItem("refund",10,"24.05.05 12:15"),
            AskPaymentHistoryItem("pay",20,"24.05.05 12:15"),
            AskPaymentHistoryItem("refund",20,"24.05.05 12:15"),
            AskPaymentHistoryItem("pay",30,"24.05.05 12:15"),
        )
        val adapter = rvAdapterAskPaymentHistory(this, items)
        binding.rvAskPaymentHistory.adapter = adapter
        binding.rvAskPaymentHistory.layoutManager = LinearLayoutManager(this)

        onBackBtnPressed()
    }
    fun onBackBtnPressed(){
        binding.includeAskPaymentHistoryTopAppBar.backBtn.setOnClickListener{finish()}
    }

}


