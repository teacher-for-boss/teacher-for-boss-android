package com.company.teacherforboss.data.api

import com.company.teacherforboss.BuildConfig
import com.company.teacherforboss.data.service.awsService
import com.company.teacherforboss.data.tokenmanager.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{
    private val tokenManager: TokenManager = TokenManager
    var mHttpLoggingInterceptor= HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    var mOkHttpClient= OkHttpClient.Builder()
        .addInterceptor(mHttpLoggingInterceptor)
        .build()

    var mRetrofit: Retrofit?=null
    val client: Retrofit?
        get(){
            if(mRetrofit ==null){
                mRetrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())//gson:google에서 만든 java용 json
                    .build()
            }
            return mRetrofit
        }

    fun getAwsService():awsService{
        if(mRetrofit==null){
            mRetrofit=Retrofit.Builder()
                .baseUrl("https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return mRetrofit!!.create(awsService::class.java)
    }
}