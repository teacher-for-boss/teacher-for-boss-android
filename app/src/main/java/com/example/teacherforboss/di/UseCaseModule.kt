package com.example.teacherforboss.di

import com.example.teacherforboss.domain.repository.AwsReository
import com.example.teacherforboss.domain.repository.CommunityRepository
import com.example.teacherforboss.domain.repository.SignupRepository
import com.example.teacherforboss.domain.usecase.BossTalkBookmarkUseCase
import com.example.teacherforboss.domain.usecase.BossTalkLikeUseCase
import com.example.teacherforboss.domain.usecase.BossTalkPostsUseCase
import com.example.teacherforboss.domain.usecase.PresignedUrlUseCase
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
    fun providesSignupUseCase(signupRepository: SignupRepository):SignupUseCase =
        SignupUseCase(signupRepository=signupRepository)

    @Provides
    @Singleton
    fun providesAwsUseCase(awsReository: AwsReository):PresignedUrlUseCase =
        PresignedUrlUseCase(awsReository=awsReository)

    @Provides
    @Singleton
    fun providesBossTalkUseCase(communityRepository: CommunityRepository):BossTalkPostsUseCase=
        BossTalkPostsUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesBossTalkBookmarkUseCase(communityRepository: CommunityRepository):BossTalkBookmarkUseCase=
        BossTalkBookmarkUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesBossTalkLikeUseCase(communityRepository: CommunityRepository):BossTalkLikeUseCase =
        BossTalkLikeUseCase(communityRepository=communityRepository)
}