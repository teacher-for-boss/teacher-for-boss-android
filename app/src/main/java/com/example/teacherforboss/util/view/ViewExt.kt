package com.example.teacherforboss.util.view

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadCircularImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .circleCrop()
        .into(this)
}