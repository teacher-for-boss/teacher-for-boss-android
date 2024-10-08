package com.company.teacherforboss.util.base

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest

object SvgBindingAdapter {
    fun preloadImage(context: Context, imageUrl:String){
        val imageLoader=ImageLoader.Builder(context)
            .componentRegistry { add(SvgDecoder(context)) }
            .build()

        val request=ImageRequest.Builder(context)
            .data(imageUrl)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()

        imageLoader.enqueue(request)
    }
    fun ImageView.loadImageFromUrl(imageUrl: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadImageFromUrl.context)) }
            .build()

        val imageRequest = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(300)
            .data(imageUrl)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .target(
                onSuccess = { result ->
                    // SVG를 Bitmap으로 변환
                    setImageDrawable(result)

                },
                onError = { error ->
                    Log.d("error",error.toString())
                }
            )
            .build()

        imageLoader.enqueue(imageRequest)
    }

    fun ImageView.loadImageFromUrlCoil(imageUrl: String) {
        this.scaleType = ImageView.ScaleType.CENTER_CROP // Center Crop 설정

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(context))
            }
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
            )
            .build()

        imageLoader.enqueue(imageRequest)
    }
}