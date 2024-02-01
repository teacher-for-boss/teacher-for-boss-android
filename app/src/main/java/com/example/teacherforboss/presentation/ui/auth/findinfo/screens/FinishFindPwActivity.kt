package com.example.teacherforboss.presentation.ui.auth.findinfo.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teacherforboss.R
import com.example.teacherforboss.presentation.ui.auth.login.LoginActivity

class FinishFindPwActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_find_pw)

        val intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}