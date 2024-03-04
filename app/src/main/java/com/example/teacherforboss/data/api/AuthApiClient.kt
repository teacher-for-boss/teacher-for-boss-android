package com.example.teacherforboss.data.api

import android.content.Context
import com.example.teacherforboss.BuildConfig
import com.example.teacherforboss.GlobalApplication
import com.example.teacherforboss.data.intercepter.AuthAuthenticator
import com.example.teacherforboss.data.intercepter.AuthInterceptor
import com.example.teacherforboss.data.tokenmanager.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.PrimitiveIterator
import javax.inject.Inject

//사용 x
//class AuthApiClient @Inject constructor(
//    @ApplicationContext val context: Context,
//    private var retrofit: Retrofit,
//
//    ) {
//    private val tokenManager: TokenManager = TokenManager
//    private val mHttpLoggingInterceptor = HttpLoggingInterceptor()
//        .setLevel(HttpLoggingInterceptor.Level.BODY)
//
//    private val mOkHttpClient = OkHttpClient.Builder()
//        .addInterceptor(mHttpLoggingInterceptor)
//        .addInterceptor(AuthInterceptor(context,tokenManager))
//        .authenticator(AuthAuthenticator(context,tokenManager))
//        .build()
//
////    private var mRetrofit: Retrofit? = null
//
//    val client: Retrofit
//        get() {
//            if (retrofit == null) {
//                retrofit = Retrofit.Builder()
//                    .baseUrl(BuildConfig.BASE_URL)
//                    .client(mOkHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//            }
//            return retrofit
//        }
//}
