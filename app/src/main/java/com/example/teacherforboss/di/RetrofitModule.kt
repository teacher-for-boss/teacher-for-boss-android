package com.example.teacherforboss.di

import android.content.Context
import com.example.teacherforboss.BuildConfig
import com.example.teacherforboss.BuildConfig.DEBUG
import com.example.teacherforboss.data.intercepter.AuthAuthenticator
import com.example.teacherforboss.data.intercepter.AuthInterceptor
import com.example.teacherforboss.data.tokenmanager.TokenManager
import com.example.teacherforboss.di.qualifier.Anonymous
import com.example.teacherforboss.di.qualifier.Auth
import com.example.teacherforboss.di.qualifier.TeacherForBoss
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideTokenManager(): TokenManager {
        return TokenManager
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Auth
    @Singleton
    fun provideAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenManager: TokenManager,
        authInterceptor:AuthInterceptor,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Provides
    @Anonymous
    @Singleton
    fun provideAnonymousOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Auth
    fun provideAuthRetrofit(@Auth okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Anonymous
    @Singleton
    fun provideAnonymousRetrofit(@Anonymous okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

//    @ExperimentalSerializationApi
//    @Provides
//    @Singleton
//    @TeacherForBoss
//    fun provideAuthRetrofitModule(@ApplicationContext context: Context, retrofit: Retrofit): AuthApiClient {
//        return AuthApiClient(context=context,retrofit)
//    }

    //@OptIn(ExperimentalSerializationApi::class)
//    @Provides
//    @Singleton
//    fun providesJson(): Json = Json {
//        isLenient = true
//        prettyPrint = true
//        explicitNulls = false
//        ignoreUnknownKeys = true
//    }
//
//    @ExperimentalSerializationApi
//    @Provides
//    @TeacherForBoss
//    @Singleton
//    fun providesTeacherForBossRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit =
//        Retrofit.Builder()
//            .baseUrl(BuildConfig.BASE_URL)
//            .client(okHttpClient)
//            .addConverterFactory(
//                json.asConverterFactory(requireNotNull("application/json".toMediaTypeOrNull())),
//            )
//            .build()


//    @Provides
//    @Singleton
//    fun providesOkHttpClient(
//        loggingInterceptor: HttpLoggingInterceptor,
//        @Auth authInterceptor: Interceptor,
//    ): OkHttpClient =
//        OkHttpClient.Builder().apply {
//            OkHttpClient.Builder().apply {
//                connectTimeout(10, TimeUnit.SECONDS)
//                writeTimeout(10, TimeUnit.SECONDS)
//                readTimeout(10, TimeUnit.SECONDS)
//                addInterceptor(authInterceptor)
//                if (DEBUG) addInterceptor(loggingInterceptor)
//            }
//        }.build()
//
//    @Provides
//    @Singleton
//    fun providesLogginInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
//
//    @Provides
//    @Singleton
//    @Auth
//    fun provideAuthInterceptor(interceptor: AuthInterceptor): Interceptor = interceptor


}
