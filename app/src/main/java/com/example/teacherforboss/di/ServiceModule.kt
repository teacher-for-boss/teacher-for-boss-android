package com.example.teacherforboss.di

import com.example.teacherforboss.data.service.SignupService
import com.example.teacherforboss.di.qualifier.Anonymous
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    fun providesSignupService(@Anonymous retrofit: Retrofit): SignupService =
        retrofit.create(SignupService::class.java)

}