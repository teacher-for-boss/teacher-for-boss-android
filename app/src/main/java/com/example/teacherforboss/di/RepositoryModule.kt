package com.example.teacherforboss.di

import com.example.teacherforboss.data.repository.ExamRepositoryImpl
import com.example.teacherforboss.data.repository.MembersRepositoryImpl
import com.example.teacherforboss.data.repository.SignupRepositoryImpl
import com.example.teacherforboss.domain.repository.ExamRepository
import com.example.teacherforboss.domain.repository.MembersRepository
import com.example.teacherforboss.domain.repository.SignupRepository
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
    abstract fun bindsMembersRepository(membersRepositoryImpl: MembersRepositoryImpl): MembersRepository

    @Binds
    @Singleton
    abstract fun bindsSignupRepository(signupRepositoryImpl: SignupRepositoryImpl):SignupRepository

    @Binds
    @Singleton
    abstract fun bindsExamRepository(examRepositoryImpl: ExamRepositoryImpl):ExamRepository
}