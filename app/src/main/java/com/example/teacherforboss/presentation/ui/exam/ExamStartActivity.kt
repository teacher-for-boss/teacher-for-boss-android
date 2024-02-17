package com.example.teacherforboss.presentation.ui.exam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityExamStartBinding
import com.example.teacherforboss.presentation.ui.main.MainActivity
import com.example.teacherforboss.util.base.BindingActivity

class ExamStartActivity : BindingActivity<ActivityExamStartBinding>(R.layout.activity_exam_start) {
    private lateinit var onBackPressed: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
        onBackPressedBtn()
    }

    private fun initLayout() {
        binding.examStartWelcomeName.text = SpannableString(
            getString(R.string.exam_start_title)
        ).apply {
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        this@ExamStartActivity,
                        R.color.main_purple_00
                    )
                ),
                SERVICE_START,
                SERVICE_END,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun addListeners(){
        binding.layoutExamStartBtn.setOnClickListener { navigateToExam() }
        binding.ivExamStartBackBtn.setOnClickListener { navigateToHome() }
    }

    private fun navigateToExam(){
        Intent(this, ExamActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun navigateToHome(){
        Intent(this, MainActivity::class.java).apply{
            startActivity(this)
            finish()
        }
    }

    private fun onBackPressedBtn() {
        onBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToHome()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressed)
    }

    companion object {
        const val SERVICE_START = 4
        const val SERVICE_END = 7
    }
}