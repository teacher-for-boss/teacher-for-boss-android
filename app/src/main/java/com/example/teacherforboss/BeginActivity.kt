package com.example.teacherforboss

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teacherforboss.databinding.ActivityBeginBinding
import com.example.teacherforboss.databinding.ActivitySignupBinding

class BeginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}