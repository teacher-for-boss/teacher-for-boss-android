package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.databinding.ActivityExchangeCompleteBinding

class ExchangeCompleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExchangeCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onCheckBtnPressed()
    }

    fun onCheckBtnPressed() {
        binding.btnCheck.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }
    }
}