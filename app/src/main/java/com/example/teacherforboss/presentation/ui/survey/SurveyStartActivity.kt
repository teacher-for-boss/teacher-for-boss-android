package com.example.teacherforboss.presentation.ui.survey

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivitySurveyStartBinding
import com.example.teacherforboss.presentation.ui.main.MainActivity
import com.example.teacherforboss.util.base.BindingActivity

class SurveyStartActivity :
    BindingActivity<ActivitySurveyStartBinding>(R.layout.activity_survey_start) {
    private lateinit var onBackPressed: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
        onBackPressedBtn()
    }

    private fun initLayout() {
        // TODO 로그인 시 저장되는 사용자 이름 쉐프에서 가져와 출력
        var text = getString(R.string.survey_start_name, "하지은")
        binding.surveyStartWelcomeName.text = text
    }

    private fun addListeners() {
        binding.layoutSurveyStartBtn.setOnClickListener { navigateToSurvey() }
        binding.ivSurveyStartBackBtn.setOnClickListener { navigateToMain() }
    }

    private fun navigateToSurvey() {
        Intent(this, SurveyActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun navigateToMain() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun onBackPressedBtn() {
        onBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToMain()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressed)
    }
}
