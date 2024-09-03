package com.company.teacherforboss.presentation

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.company.teacherforboss.R

class FullScreenImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

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