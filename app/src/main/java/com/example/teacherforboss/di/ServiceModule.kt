package com.example.teacherforboss.di

import com.example.teacherforboss.data.service.MembersService
import com.example.teacherforboss.data.service.SignupService
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
    fun providesMembersService(@TeacherForBoss retrofit: Retrofit): MembersService =
        retrofit.create(MembersService::class.java)

    @Provides
    @Singleton
    fun providesSignupService(@TeacherForBoss retrofit: Retrofit): SignupService =
        retrofit.create(SignupService::class.java)
}