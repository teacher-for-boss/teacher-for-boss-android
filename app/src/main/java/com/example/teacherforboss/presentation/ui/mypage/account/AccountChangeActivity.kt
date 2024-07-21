package com.example.teacherforboss.presentation.ui.mypage.exchange

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.teacherforboss.MainActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityAccountChangeBinding
import com.example.teacherforboss.presentation.ui.auth.signup.teacher.BankFragment
import com.example.teacherforboss.databinding.ActivityExchangeBinding

class AccountChangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountChangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AccountChangeFragment())
            .commit()
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