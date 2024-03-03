package com.example.teacherforboss.di

import com.example.teacherforboss.data.api.AuthApiClient
import com.example.teacherforboss.data.service.ExamService
import com.example.teacherforboss.data.service.MembersService
import com.example.teacherforboss.data.service.SignupService
import com.example.teacherforboss.di.qualifier.Anonymous
import com.example.teacherforboss.di.qualifier.Auth
import com.example.teacherforboss.di.qualifier.TeacherForBoss
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesMembersService(@Auth retrofit: Retrofit):MembersService =
        retrofit.create(MembersService::class.java)
    @Provides
    @Singleton
    fun providesSignupService(@Anonymous retrofit: Retrofit): SignupService =
        retrofit.create(SignupService::class.java)

    @Provides
    @Singleton
    fun providesExamService(@Auth retrofit:Retrofit):ExamService=
        retrofit.create(ExamService::class.java)
}