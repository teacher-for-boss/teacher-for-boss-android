package com.example.teacherforboss.di

import com.example.teacherforboss.domain.repository.AwsReository
import com.example.teacherforboss.domain.repository.CommunityRepository
import com.example.teacherforboss.domain.repository.SignupRepository
import com.example.teacherforboss.domain.usecase.PresignedUrlUseCase
import com.example.teacherforboss.domain.usecase.SignupUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkCommentDisLikeUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkCommentLikeUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkBodyUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkBookmarkUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkLikeUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkBodyUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkBookmarkUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkCommentListUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkCommentUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkLikeUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkModifyBodyUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkPostsUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkSearchUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossUploadPostUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerListUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerPostUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkDeleteBodyUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkModifyAnswerUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkModifyBodyUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkQuestionsUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkSelectUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherUploadPostUseCase
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
    fun providesTeacherTalkUseCase(communityRepository: CommunityRepository):TeacherTalkQuestionsUseCase=
        TeacherTalkQuestionsUseCase(communityRepository=communityRepository)


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
    fun providesBossTalkCommentListUseCase(communityRepository: CommunityRepository): BossTalkCommentListUseCase =
        BossTalkCommentListUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesBossTalkCommentUseCase(communityRepository: CommunityRepository):BossTalkCommentUseCase =
        BossTalkCommentUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesBossTalkCommentLikeUseCase(communityRepository: CommunityRepository): BossTalkCommentLikeUseCase =
        BossTalkCommentLikeUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesBossTalkCommentdisLikeUseCase(communityRepository: CommunityRepository): BossTalkCommentDisLikeUseCase =
        BossTalkCommentDisLikeUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkBodyUseCase(communityRepository: CommunityRepository): TeacherTalkBodyUseCase =
        TeacherTalkBodyUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkLikeUseCase(communityRepository: CommunityRepository): TeacherTalkLikeUseCase =
        TeacherTalkLikeUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkBookmarkUseCase(communityRepository: CommunityRepository): TeacherTalkBookmarkUseCase =
        TeacherTalkBookmarkUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherUploadPostUseCase(communityRepository: CommunityRepository): TeacherUploadPostUseCase =
        TeacherUploadPostUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkModifyBodyUseCase(communityRepository: CommunityRepository): TeacherTalkModifyBodyUseCase =
        TeacherTalkModifyBodyUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkDeleteBodyUseCase(communityRepository: CommunityRepository): TeacherTalkDeleteBodyUseCase =
        TeacherTalkDeleteBodyUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkAnswerListUseCase(communityRepository: CommunityRepository): TeacherTalkAnswerListUseCase =
        TeacherTalkAnswerListUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkAnswerPostUseCase(communityRepository: CommunityRepository): TeacherTalkAnswerPostUseCase =
        TeacherTalkAnswerPostUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkModifyAnswerUseCase(communityRepository: CommunityRepository): TeacherTalkModifyAnswerUseCase =
        TeacherTalkModifyAnswerUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkSelectUseCase(communityRepository: CommunityRepository): TeacherTalkSelectUseCase =
        TeacherTalkSelectUseCase(communityRepository = communityRepository)

}