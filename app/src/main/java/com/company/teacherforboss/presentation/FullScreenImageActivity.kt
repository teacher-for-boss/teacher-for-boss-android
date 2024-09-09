package com.company.teacherforboss.presentation

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityFullScreenImageBinding

class FullScreenImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullScreenImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenImageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val imageUrl = intent.getStringExtra("IMAGE_URL")
        if (imageUrl != null) {
            // Use Glide or any other library to load the image into the ImageView
            Glide.with(this)
                .load(imageUrl)
                .into(findViewById(R.id.image_full))
        }

        findViewById<ImageView>(R.id.image_full).setOnClickListener {
            supportFinishAfterTransition()
        }
    }
}