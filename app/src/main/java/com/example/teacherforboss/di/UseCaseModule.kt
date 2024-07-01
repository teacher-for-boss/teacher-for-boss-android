package com.example.teacherforboss.di

import com.example.teacherforboss.domain.repository.AwsReository
import com.example.teacherforboss.domain.repository.CommunityRepository
import com.example.teacherforboss.domain.repository.SignupRepository
import com.example.teacherforboss.domain.usecase.BossTalkBodyUseCase
import com.example.teacherforboss.domain.usecase.BossTalkBookmarkUseCase
import com.example.teacherforboss.domain.usecase.BossTalkCommentUseCase
import com.example.teacherforboss.domain.usecase.BossTalkLikeUseCase
import com.example.teacherforboss.domain.usecase.BossTalkModifyBodyUseCase
import com.example.teacherforboss.domain.usecase.BossTalkPostsUseCase
import com.example.teacherforboss.domain.usecase.BossTalkSearchUseCase
import com.example.teacherforboss.domain.usecase.BossUploadPostUseCase
import com.example.teacherforboss.domain.usecase.PresignedUrlUseCase
import com.example.teacherforboss.domain.usecase.SignupUseCase
import com.example.teacherforboss.domain.usecase.TeacherTalkBodyUseCase
import com.example.teacherforboss.domain.usecase.TeacherTalkBookmarkUseCase
import com.example.teacherforboss.domain.usecase.TeacherTalkLikeUseCase
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
    fun providesBossUploadPostUseCase(communityRepository: CommunityRepository):BossUploadPostUseCase=
        BossUploadPostUseCase(communityRepository=communityRepository)


    @Provides
    @Singleton
    fun providesBossTalkBookmarkUseCase(communityRepository: CommunityRepository):BossTalkBookmarkUseCase=
        BossTalkBookmarkUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesBossTalkLikeUseCase(communityRepository: CommunityRepository):BossTalkLikeUseCase =
        BossTalkLikeUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesBossTalkBodyUseCase(communityRepository: CommunityRepository):BossTalkBodyUseCase =
        BossTalkBodyUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesBossTalkSearchUseCase(communityRepository: CommunityRepository):BossTalkSearchUseCase =
        BossTalkSearchUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesBossTalkModifyBodyUseCase(communityRepository: CommunityRepository):BossTalkModifyBodyUseCase =
        BossTalkModifyBodyUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesBossTalkCommentUseCase(communityRepository: CommunityRepository):BossTalkCommentUseCase =
        BossTalkCommentUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkBodyUseCase(communityRepository: CommunityRepository):TeacherTalkBodyUseCase =
        TeacherTalkBodyUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkLikeUseCase(communityRepository: CommunityRepository): TeacherTalkLikeUseCase =
        TeacherTalkLikeUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkBookmarkUseCase(communityRepository: CommunityRepository): TeacherTalkBookmarkUseCase =
        TeacherTalkBookmarkUseCase(communityRepository = communityRepository)

}