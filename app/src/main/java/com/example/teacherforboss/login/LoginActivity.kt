package com.example.teacherforboss.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)

        var id=binding.idBox.text
        var pw=binding.pwBox.text
        setContentView(R.layout.activity_login)

        //retrofit
    }
}