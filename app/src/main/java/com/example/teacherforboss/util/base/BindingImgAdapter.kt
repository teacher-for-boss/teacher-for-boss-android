package com.example.teacherforboss.util.base

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object BindingImgAdapter {
    @BindingAdapter("profileImg")
    fun bindImage(imageView: ImageView, imgUrl:String){
        imgUrl?.let {
            val imgUri=imgUrl.toUri().buildUpon().scheme("https").build()

            Glide.with(imageView.context)
                .load(imgUri)
                .apply(RequestOptions())
                .into(imageView)
        }

    }
}