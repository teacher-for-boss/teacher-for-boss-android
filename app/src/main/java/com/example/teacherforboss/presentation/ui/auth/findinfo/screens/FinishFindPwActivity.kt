package com.example.teacherforboss.presentation.ui.auth.findinfo.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityFinishFindPwBinding
import com.example.teacherforboss.presentation.ui.auth.login.LoginActivity

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