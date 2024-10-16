package com.company.teacherforboss.di

import android.content.Context
import com.company.teacherforboss.data.intercepter.AuthAuthenticator
import com.company.teacherforboss.data.intercepter.AuthInterceptor
import com.company.teacherforboss.data.tokenmanager.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor(@ApplicationContext context: Context, tokenManager:TokenManager):AuthInterceptor{
        return AuthInterceptor(context, tokenManager = tokenManager)
    }

    @Provides
    @Singleton
    fun provideAuthAuthenticator(@ApplicationContext context: Context,tokenManager: TokenManager): AuthAuthenticator {
        return AuthAuthenticator(context,tokenManager)
    }
}