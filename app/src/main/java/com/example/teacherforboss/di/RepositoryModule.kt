package com.example.teacherforboss.di

import com.example.teacherforboss.data.repository.SignupRepositoryImpl
import com.example.teacherforboss.data.repository.UserRepositoryImpl
import com.example.teacherforboss.domain.repository.SignupRepository
import com.example.teacherforboss.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindsSignupRepository(signupRepositoryImpl: SignupRepositoryImpl):SignupRepository

}