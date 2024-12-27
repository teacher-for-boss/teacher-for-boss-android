package com.company.teacherforboss.util.base

import android.content.Context
import android.net.Uri
import android.util.Log
import com.company.teacherforboss.data.api.ApiClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadUtil(private val context: Context) {
    private val s3Service=ApiClient.getAwsService()

    // 프로필 이미지 업로드
    suspend fun uploadProfileImage_v2(url: String, imgUri: Uri, fileType: String) {
        val imgFile = FileUtils.getFileFromUri(context, imgUri)
        val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), imgFile)

        try {
            val response = s3Service.uploadImg(url, requestBody, fileType)
            if (response.isSuccessful) {
                Log.d("upload", "Upload success")
            } else {
                Log.e("upload", "Upload error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("upload", "Upload failed: ${e.message}")
        }
    }

    // 여러 이미지를 업로드
    suspend fun uploadPostImage(urlList: List<String>, requestBodyList: List<RequestBody>, fileType: String) {
        urlList.forEachIndexed { index, url ->
            try {
                val response = s3Service.uploadImg(url, requestBodyList[index], fileType)
                if (response.isSuccessful) {
                    Log.d("upload", "Upload success for $url")
                } else {
                    Log.e("upload", "Upload error for $url: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("upload", "Upload failed for $url: ${e.message}")
            }
        }
    }

    // URI를 RequestBody로 변환
    fun convertUriToImg(uriList: List<Uri>): List<RequestBody> {
        return uriList.map { uri ->
            val imgFile = FileUtils.getFileFromUri(context, uri)
            RequestBody.create("image/*".toMediaTypeOrNull(), imgFile)
        }
    }

//    fun uploadProfileImage(url:String,imgUri: Uri,fileType:String){
//        val imgFile=FileUtils.getFileFromUri(context,imgUri)
//        val requestBody=RequestBody.create("image/*".toMediaTypeOrNull(),imgFile)

//        val call=s3Service.uploadImg(url,requestBody,fileType)
//        call.enqueue(object :Callback<Void>{
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                if(response.isSuccessful) Log.d("upload","success")
//                else Log.e("upload","upload error")
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//                Log.e("upload","upload error:${t.message}")
//            }
//        })
//    }
//
//    fun uploadPostImage(urlList:List<String>,requestBodyList:List<RequestBody>,fileType:String){
//        requestBodyList.forEachIndexed { index,requestBody->
//            val call=s3Service.uploadImg(urlList[index],requestBody,fileType)
//            call.enqueue(object :Callback<Void>{
//                override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                    if(response.isSuccessful) Log.d("upload","success")
//                    else Log.e("upload","upload error")
//                }
//
//                override fun onFailure(call: Call<Void>, t: Throwable) {
//                    Log.e("upload","upload error:${t.message}")
//                }
//            })
//
//        }
//
//    }
//    fun convert_UritoImg(uriList:List<Uri>):List<RequestBody>{
//        val requestBodyList= mutableListOf<RequestBody>()
//        uriList.forEachIndexed { index, uri ->
//            val imgFile=FileUtils.getFileFromUri(context,uri)
//            val requestBody=RequestBody.create("image/*".toMediaTypeOrNull(),imgFile)
//            requestBodyList.add(requestBody)
//        }
//        return requestBodyList
//    }

}