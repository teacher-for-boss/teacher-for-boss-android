package com.example.teacherforboss.util.base

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.teacherforboss.data.api.ApiClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadUtil(private val context: Context) {
    private val s3Service=ApiClient.getAwsService()

    fun uploadProfileImage(url:String,imgUri: Uri){
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
    fun uploadPostImage(urlList:List<String>,requestBodyList:List<RequestBody>){

        requestBodyList.forEachIndexed { index,requestBody->
            val call=s3Service.uploadImg(urlList[index],requestBody)
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
    fun convert_UritoImg(uriList:List<Uri>):List<RequestBody>{
        val requestBodyList= mutableListOf<RequestBody>()
        uriList.forEachIndexed { index, uri ->
            val imgFile=FileUtils.getFileFromUri(context,uri)
            val requestBody=RequestBody.create("image/*".toMediaTypeOrNull(),imgFile)
            requestBodyList.add(requestBody)
        }
        return requestBodyList
    }

}