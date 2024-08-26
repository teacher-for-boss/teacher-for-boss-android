package com.company.teacherforboss.util.base

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object BindingImgAdapter {
    @BindingAdapter("profileImg")
    fun bindImage(imageView: ImageView, imgUrl:String){
        imgUrl?.let {
            val imgUri=imgUrl.toUri().buildUpon().scheme("https").build()

            Glide.with(imageView.context)
                .load(imgUri)
                .apply(RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                )
                .into(imageView)
        }

    }

    fun bindImgUri(imageView: ImageView,imageUri: Uri){
        Glide.with(imageView.context)
            .load(imageUri)
            .apply(RequestOptions())
            .into(imageView)
    }
    fun bindProfileImgUrl(imageView: ImageView, url:String){
        Glide.with(imageView.context)
            .load(url)
            .fitCenter()
            .centerCrop()
            .apply(RequestOptions()
                .override(80,80)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false))
            .into(imageView)
    }

    fun bindProfileImgUri(imageView: ImageView,uri: Uri){
        Glide.with(imageView.context)
            .load(uri)
            .fitCenter()
            .centerCrop()
            .apply(RequestOptions()
                .override(80,80)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false))
            .into(imageView)
    }
}