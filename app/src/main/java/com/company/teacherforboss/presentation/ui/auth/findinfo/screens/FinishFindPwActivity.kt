package com.company.teacherforboss.presentation.ui.auth.findinfo.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.teacherforboss.databinding.ActivityFinishFindPwBinding
import com.company.teacherforboss.presentation.ui.auth.login.LoginActivity

class FinishFindPwActivity : AppCompatActivity() {
    lateinit var binding:ActivityFinishFindPwBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishFindPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gotoLogin.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)

        }
    }
}