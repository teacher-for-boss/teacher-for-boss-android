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
import com.company.teacherforboss.presentation.ui.auth.login.LoginActivity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_NICKNAME
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_ROLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SignupFinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupFinishBinding
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupFinishBinding.inflate(layoutInflater)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_signup_finish)
        binding.lifecycleOwner=this
        val role = intent.getIntExtra(USER_ROLE,0)

        setContentView(binding.root)

        initView(role)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent=Intent(this,LoginActivity::class.java).apply {
//                putExtra(FRAGMENT_DESTINATION, HOME)
            }
            startActivity(intent)
            finish()
        }, 2000)




    }
    fun initView(int:Int?){
        val nickname = intent.getStringExtra(USER_NICKNAME)?: ""
        var text = ""
        if (int == 1) {
            text = "$nickname 보스,\n회원가입을 축하합니다!"
            binding.dialog.visibility = View.INVISIBLE
            binding.confetti2.visibility = View.INVISIBLE
        }
        else if (int == 2) {
            text = "$nickname 티쳐,\n회원가입을 축하합니다!"
            binding.dialogName.text = "$nickname 티쳐"
            binding.confetti1.visibility = View.INVISIBLE
        }

        val spannable = SpannableString(text)
        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.Purple500)) // 원하는 색상으로 변경
        spannable.setSpan(colorSpan, 0, nickname.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.titleText.text = spannable
    }

    companion object{
        const val FRAGMENT_DESTINATION="FRAGMENT_DESTINATION"
        const val HOME="HOME"
    }

}