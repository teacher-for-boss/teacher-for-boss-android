package com.example.teacherforboss.presentation.ui.exam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityExamStartBinding
import com.example.teacherforboss.presentation.ui.main.MainActivity
import com.example.teacherforboss.util.base.BindingActivity

class ExamStartActivity : BindingActivity<ActivityExamStartBinding>(R.layout.activity_exam_start) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
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
}