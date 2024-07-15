package com.example.teacherforboss.presentation.ui.mypage.exchange

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.teacherforboss.MainActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityExchangeBinding

class ExchangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExchangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            // Get the intent and check if it should start with ExchangeFragment2
            if (intent.getBooleanExtra("startWithFragment2", false)) {
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, ExchangeFragment2())
                }
            } else {
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, ExchangeFragment())
                }
            }
        }
        onBackBtnPressed()
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
    fun onBackBtnPressed(){
        binding.backBtn.setOnClickListener {
            val intent= Intent(this, ExchangeActivity::class.java).apply {
                putExtra("FRAGMENT_DESTINATION","EXCHANGE")
            }
            startActivity(intent)
        }

    }
}