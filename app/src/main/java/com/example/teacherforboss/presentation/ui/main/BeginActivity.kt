package com.example.teacherforboss.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teacherforboss.databinding.ActivityBeginBinding
import com.example.teacherforboss.presentation.ui.auth.login.LoginActivity

class BeginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginBinding.inflate(layoutInflater)
        binding.gotoLogin.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)

    }
}