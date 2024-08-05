package com.company.teacherforboss.util.base

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("image")
fun setImageResource(imageView: ImageView, resId: Int) {
    val drawable = ContextCompat.getDrawable(imageView.context, resId)
    imageView.setImageDrawable(drawable)
}

@BindingAdapter("textColor")
fun setTextColor(textView: TextView, resId: Int) {
    val colorRes = ContextCompat.getColor(textView.context, resId)
    textView.setTextColor(colorRes)
}

@BindingAdapter("backgroundTint")
fun setBackGroundTint(textView: TextView, resId: Int) {
    val colorRes = ContextCompat.getColor(textView.context, resId)
    textView.backgroundTintList = ColorStateList.valueOf(colorRes)
}
