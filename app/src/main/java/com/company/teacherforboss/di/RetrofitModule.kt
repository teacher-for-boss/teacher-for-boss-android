package com.company.teacherforboss.di

import com.company.teacherforboss.BuildConfig
import com.company.teacherforboss.data.intercepter.AuthAuthenticator
import com.company.teacherforboss.data.intercepter.AuthInterceptor
import com.company.teacherforboss.data.tokenmanager.TokenManager
import com.company.teacherforboss.di.qualifier.Anonymous
import com.company.teacherforboss.di.qualifier.Auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

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

}
