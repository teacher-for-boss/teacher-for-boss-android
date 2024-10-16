package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityExchangeBinding
import com.company.teacherforboss.util.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeActivity : BindingActivity<ActivityExchangeBinding>(R.layout.activity_exchange) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            if (intent.getBooleanExtra("startWithFragment2", false)) {
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, ExchangeFragment2())
                }
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ExchangeFragment())
                    .commit()
            }
        }
        binding.backBtn.setOnClickListener {
            handleBackButton()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun handleBackButton() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment is ExchangeFragment2) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}
