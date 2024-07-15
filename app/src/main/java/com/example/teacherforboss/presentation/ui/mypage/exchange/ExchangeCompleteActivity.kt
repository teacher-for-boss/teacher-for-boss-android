package com.example.teacherforboss.presentation.ui.mypage.exchange

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.teacherforboss.databinding.ActivityExchangeCompleteBinding

class ExchangeCompleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExchangeCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onCheckBtnPressed()
    }

    fun onCheckBtnPressed(){
        binding.btnCheck.setOnClickListener {
            val intent= Intent(this, ExchangeActivity::class.java).apply {
                putExtra("FRAGMENT_DESTINATION","EXCHANGE")
            }
            startActivity(intent)
        }

    }
}