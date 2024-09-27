package com.company.teacherforboss.presentation.ui.mypage.subscription

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivitySubscriptionBinding
import com.company.teacherforboss.presentation.ui.mypage.subscription.SubscriptionFragment
import com.company.teacherforboss.util.base.BindingActivity

class SubscriptionActivity : BindingActivity<ActivitySubscriptionBinding>(R.layout.activity_subscription) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SubscriptionFragment())
                .commit()
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
        finish()
    }
}
