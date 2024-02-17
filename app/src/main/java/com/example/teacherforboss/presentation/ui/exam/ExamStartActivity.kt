package com.example.teacherforboss.presentation.ui.exam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityExamStartBinding
import com.example.teacherforboss.presentation.ui.main.MainActivity
import com.example.teacherforboss.util.base.BindingActivity

class ExamStartActivity : BindingActivity<ActivityExamStartBinding>(R.layout.activity_exam_start) {
    private lateinit var onBackPressed: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
        onBackPressedBtn()
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
}