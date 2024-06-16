package com.example.teacherforboss.util.base

import android.content.Context
import android.util.Log
import com.example.teacherforboss.data.api.ApiClient
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadUtil(private val context: Context, private val viewModel:SignupViewModel) {
    private val s3Service=ApiClient.getAwsService()

    fun uploadImage(){
        val url=viewModel.profilePresignedUrl.value?:return
        val imgUri=viewModel.profileImgUri.value?:return

        val imgFile=FileUtils.getFileFromUri(context,imgUri)
        val requestBody=RequestBody.create("image/*".toMediaTypeOrNull(),imgFile)

        val call=s3Service.uploadImg(url,requestBody)
        call.enqueue(object :Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful) Log.d("upload","success")
                else Log.e("upload","upload error")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("upload","upload error:${t.message}")
            }
        })
    }

}