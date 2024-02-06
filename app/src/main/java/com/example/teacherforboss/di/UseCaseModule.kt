package com.example.teacherforboss.di

import com.example.teacherforboss.domain.repository.MembersRepository
import com.example.teacherforboss.domain.usecase.PostSurveyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun providesPostSurveyUseCase(membersRepository: MembersRepository): PostSurveyUseCase =
        PostSurveyUseCase(membersRepository = membersRepository)
}