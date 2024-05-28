package com.example.teacherforboss.util.base

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

object SvgBindingAdapter {
    fun ImageView.loadImageFromUrl(imageUrl: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadImageFromUrl.context)) }
            .build()

        val imageRequest = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(300)
            .data(imageUrl)
            .target(
                onSuccess = { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    this.setImageBitmap(bitmap)
                },
                onError = { error ->
                    // 오류 처리 (필요한 경우)
                    Log.d("error",error.toString())
                }
            )
            .build()

        imageLoader.enqueue(imageRequest)
    }
}