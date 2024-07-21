package com.example.teacherforboss.di

import com.example.teacherforboss.domain.repository.AuthRepository
import com.example.teacherforboss.domain.repository.AwsReository
import com.example.teacherforboss.domain.repository.CommunityRepository
import com.example.teacherforboss.domain.repository.HomeRepository
import com.example.teacherforboss.domain.repository.MemberRepository
import com.example.teacherforboss.domain.repository.SignupRepository
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkDeletePostUseCase
import com.example.teacherforboss.domain.usecase.PresignedUrlUseCase
import com.example.teacherforboss.domain.usecase.SignupUseCase
import com.example.teacherforboss.domain.usecase.Member.AccountUsecase
import com.example.teacherforboss.domain.usecase.auth.LogoutUsecase
import com.example.teacherforboss.domain.usecase.auth.WithdrawUsecase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnsUseCase
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
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerDislikeUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerLikeUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerListUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerPostUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkDeleteBodyUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkModifyAnswerUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkModifyBodyUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkQuestionsUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkSelectUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherUploadPostUseCase
import com.example.teacherforboss.domain.usecase.home.GetBossTalkPopularPostUseCase
import com.example.teacherforboss.domain.usecase.home.GetTeacherTalkPopularPostUseCase
import com.example.teacherforboss.domain.usecase.home.GetWeeklyBestTeacherUseCase
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
    fun providesBossTalkDeletePostUseCase(communityRepository: CommunityRepository):BossTalkDeletePostUseCase =
        BossTalkDeletePostUseCase(communityRepository=communityRepository)

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
    fun providesTeacherTalkAnsUseCase(communityRepository: CommunityRepository): TeacherTalkAnsUseCase =
        TeacherTalkAnsUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkModifyAnswerUseCase(communityRepository: CommunityRepository): TeacherTalkModifyAnswerUseCase =
        TeacherTalkModifyAnswerUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkSelectUseCase(communityRepository: CommunityRepository): TeacherTalkSelectUseCase =
        TeacherTalkSelectUseCase(communityRepository = communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkAnswerLikeUseCase(communityRepository: CommunityRepository): TeacherTalkAnswerLikeUseCase =
        TeacherTalkAnswerLikeUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesTeacherTalkAnswerDislikeUseCase(communityRepository: CommunityRepository): TeacherTalkAnswerDislikeUseCase =
        TeacherTalkAnswerDislikeUseCase(communityRepository=communityRepository)

    @Provides
    @Singleton
    fun providesGetBossTalkPopularPostUseCase(homeRepository: HomeRepository): GetBossTalkPopularPostUseCase =
        GetBossTalkPopularPostUseCase(homeRepository = homeRepository)

    @Provides
    @Singleton
    fun providesGetTeacherTalkPopularPostUseCase(homeRepository: HomeRepository): GetTeacherTalkPopularPostUseCase =
        GetTeacherTalkPopularPostUseCase(homeRepository = homeRepository)

    @Provides
    @Singleton
    fun providesGetWeeklyBestTeacherUseCase(homeRepository: HomeRepository): GetWeeklyBestTeacherUseCase =
        GetWeeklyBestTeacherUseCase(homeRepository = homeRepository)

    @Provides
    @Singleton
    fun providesLogoutUseCase(authRepository: AuthRepository): LogoutUsecase =
        LogoutUsecase(authRepository=authRepository)

    @Provides
    @Singleton
    fun providesWithdrawUsecase(authRepository: AuthRepository):WithdrawUsecase=
        WithdrawUsecase(authRepository=authRepository)

    @Provides
    @Singleton
    fun providesAccountUsecase(memberRepository: MemberRepository): AccountUsecase =
        AccountUsecase(memberRepository)

}