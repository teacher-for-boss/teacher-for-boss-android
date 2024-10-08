package com.company.teacherforboss.presentation.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivitySignupFinishBinding
import com.company.teacherforboss.databinding.ActivitySignupJudgeBinding
import com.company.teacherforboss.presentation.ui.auth.login.LoginActivity
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_NICKNAME
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_ROLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SignupJudgeActivity : BindingActivity<ActivitySignupJudgeBinding>(R.layout.activity_signup_judge) {
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupJudgeBinding.inflate(layoutInflater)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_signup_judge)
        binding.lifecycleOwner=this

        setContentView(binding.root)
        initView()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent=Intent(this,LoginActivity::class.java).apply { }
            startActivity(intent)
            finish()
        }, 5000)
    }
    fun initView(){

        val titleText = getString(R.string.sign_up_judge_tilte)
        val subTitleText = getString(R.string.sign_up_judge_sub_tilte)
        val nickname = intent.getStringExtra(USER_NICKNAME)?: ""
        binding.dialogName.text = "$nickname 티처"

        val spannable = SpannableString(titleText)
        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.Purple500)) // 원하는 색상으로 변경
        spannable.setSpan(colorSpan, 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.titleText.text = spannable
        binding.subTitleText.text = subTitleText
    }

    companion object{
        const val FRAGMENT_DESTINATION="FRAGMENT_DESTINATION"
        const val HOME="HOME"
    }

}