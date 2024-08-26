package com.company.teacherforboss.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.company.teacherforboss.databinding.ActivityFullScreenImageBinding

class FullscreenImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullScreenImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl = intent.getStringExtra("IMAGE_URL")

        imageUrl?.let {
            Glide.with(this).load(it).into(binding.fullScreenImageView)
        }

        binding.fullScreenImageView.setOnClickListener {
            finish() // 이미지를 클릭하면 전체화면 액티비티를 종료합니다.
        }
    }
}