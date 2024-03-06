package com.example.teacherforboss.di

import com.example.teacherforboss.domain.repository.ExamRepository
import com.example.teacherforboss.domain.repository.MembersRepository
import com.example.teacherforboss.domain.repository.SignupRepository
import com.example.teacherforboss.domain.usecase.ExamResultUseCase
import com.example.teacherforboss.domain.usecase.ExamResultWrongNotesUseCase
import com.example.teacherforboss.domain.usecase.PostSurveyUseCase
import com.example.teacherforboss.domain.usecase.ProfileUseCase
import com.example.teacherforboss.domain.usecase.SignupUseCase
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

    @Provides
    @Singleton
    fun providesSignupUseCase(signupRepository: SignupRepository):SignupUseCase =
        SignupUseCase(signupRepository=signupRepository)

    @Provides
    @Singleton
    fun providesProfileUseCase(membersRepository: MembersRepository):ProfileUseCase=
        ProfileUseCase(membersRepository=membersRepository)

    @Provides
    @Singleton
    fun providesExamResultUseCase(examRepository: ExamRepository):ExamResultUseCase=
        ExamResultUseCase(examRepository=examRepository)

    @Provides
    @Singleton
    fun providesExamResultWrongNotesUseCase(examRepository: ExamRepository):ExamResultWrongNotesUseCase=
        ExamResultWrongNotesUseCase(examRepository)

}