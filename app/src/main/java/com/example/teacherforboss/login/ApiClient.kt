package com.example.teacherforboss.login

import android.content.Context
import com.example.teacherforboss.GlobalApplication
//import dagger.hilt.android.qualifiers.ActivityContext
//import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

val BASEURL="http://13.209.77.233:8080"

object ApiClient{
    private val tokenManager:TokenManager=TokenManager
    var mHttpLoggingInterceptor=HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    var mOkHttpClient=OkHttpClient
        .Builder()
        .addInterceptor(mHttpLoggingInterceptor)
        .addInterceptor(AuthInterceptor(getAppContenxt(), tokenManager))
        .authenticator(AuthAuthenticator(TokenManager, getAppContenxt()))
        .build()

    var mRetrofit:Retrofit?=null
    val client:Retrofit?
        get(){
            if(mRetrofit==null){
                mRetrofit=Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())//gson:google에서 만든 java용 json
                    .build()
            }
            return mRetrofit
        }
}

private fun getAppContenxt(): Context {
    return GlobalApplication.instance.applicationContext
}

