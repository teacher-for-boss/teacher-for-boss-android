package com.example.teacherforboss.presentation.ui.survey

import android.content.Intent
import android.os.Bundle
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivitySurveyStartBinding
import com.example.teacherforboss.presentation.ui.auth.login.LoginActivity
import com.example.teacherforboss.util.base.BindingActivity

class SurveyStartActivity :
    BindingActivity<ActivitySurveyStartBinding>(R.layout.activity_survey_start) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        // TODO 로그인 시 저장되는 사용자 이름 쉐프에서 가져와 출력
        String.format(resources.getString(R.string.survey_start_name), "김빛나")
    }

    private fun addListeners() {
        binding.layoutSurveyStartBtn.setOnClickListener { navigateToSurvey() }
        binding.ivSurveyStartBackBtn.setOnClickListener { navigateToLogin() }
    }

    private fun navigateToSurvey() {
        Intent(this, SurveyActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun navigateToLogin() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}
