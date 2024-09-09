package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityAccountChangeBinding
import com.company.teacherforboss.util.base.BindingActivity

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountChangeActivity : BindingActivity<ActivityAccountChangeBinding>(R.layout.activity_account_change) {
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
            with(supportFragmentManager){
                if(backStackEntryCount >= 1) popBackStack()
                else finish()
            }
        }
    }
}