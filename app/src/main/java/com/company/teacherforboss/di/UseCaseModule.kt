package com.company.teacherforboss.di

import com.company.teacherforboss.domain.repository.AuthRepository
import com.company.teacherforboss.domain.repository.AwsReository
import com.company.teacherforboss.domain.repository.CommunityRepository
import com.company.teacherforboss.domain.repository.HomeRepository
import com.company.teacherforboss.domain.repository.MemberRepository
import com.company.teacherforboss.domain.repository.MyPageRepository
import com.company.teacherforboss.domain.repository.PaymentRepository
import com.company.teacherforboss.domain.repository.SignupRepository
import com.company.teacherforboss.domain.usecase.Member.AccountUsecase
import com.company.teacherforboss.domain.usecase.Member.ModifyBossProfileUseCase
import com.company.teacherforboss.domain.usecase.Member.ModifyTeacherProfileUseCase
import com.company.teacherforboss.domain.usecase.Member.ProfileUseCase
import com.company.teacherforboss.domain.usecase.Member.TeacherDetailProfileUseCase
import com.company.teacherforboss.domain.usecase.PresignedUrlUseCase
import com.company.teacherforboss.domain.usecase.SignupUseCase
import com.company.teacherforboss.domain.usecase.auth.LogoutUsecase
import com.company.teacherforboss.domain.usecase.auth.WithdrawUsecase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkBodyUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkBookmarkUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkCommentDeleteUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkCommentDisLikeUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkCommentLikeUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkCommentListUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkCommentUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkDeletePostUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkLikeUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkModifyBodyUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkPostsUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkSearchUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossUploadPostUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnsUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerDislikeUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerLikeUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerListUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerPostUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkBodyUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkBookmarkUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkDeleteBodyUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkLikeUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkModifyAnswerUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkModifyBodyUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkQuestionsUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkSearchUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkSelectUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherUploadPostUseCase
import com.company.teacherforboss.domain.usecase.home.GetBossTalkPopularPostUseCase
import com.company.teacherforboss.domain.usecase.home.GetTeacherTalkPopularPostUseCase
import com.company.teacherforboss.domain.usecase.home.GetWeeklyBestTeacherUseCase
import com.company.teacherforboss.domain.usecase.mypage.BookmarkedPostsUseCase
import com.company.teacherforboss.domain.usecase.mypage.MyPageAnsweredQuestionUseCase
import com.company.teacherforboss.domain.usecase.mypage.BookmarkedQuestionsUseCase
import com.company.teacherforboss.domain.usecase.payment.BankAccountChangeUseCase
import com.company.teacherforboss.domain.usecase.payment.BankAccountUseCase
import com.company.teacherforboss.domain.usecase.payment.ExchangeUseCase
import com.company.teacherforboss.domain.usecase.payment.TeacherPointUseCase
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
    fun providesBossTalkCommentDelete(communityRepository: CommunityRepository): BossTalkCommentDeleteUseCase =
        BossTalkCommentDeleteUseCase(communityRepository = communityRepository)

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
    fun providesTeacherTalkSearchUseCase(communityRepository: CommunityRepository): TeacherTalkSearchUseCase =
        TeacherTalkSearchUseCase(communityRepository = communityRepository)

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

    @Provides
    @Singleton
    fun providesBankAccountUseCase(paymentRepository: PaymentRepository): BankAccountUseCase =
        BankAccountUseCase(paymentRepository)
    @Provides
    @Singleton
    fun providesBankAccountChangeUseCase(paymentRepository: PaymentRepository): BankAccountChangeUseCase =
        BankAccountChangeUseCase(paymentRepository)

    @Provides
    @Singleton
    fun providesProfile(memberRepository: MemberRepository): ProfileUseCase = ProfileUseCase(memberRepository)

    @Provides
    @Singleton
    fun providesMyPageAnsweredQuestionUseCase(myPageRepository: MyPageRepository): MyPageAnsweredQuestionUseCase =
        MyPageAnsweredQuestionUseCase(myPageRepository)

    @Provides
    @Singleton
    fun providesBookmarkedQuestionsUseCase(myPageRepository: MyPageRepository): BookmarkedQuestionsUseCase =
        BookmarkedQuestionsUseCase(myPageRepository)

    @Provides
    @Singleton
    fun providesBookmarkedPostsUseCase(myPageRepository: MyPageRepository): BookmarkedPostsUseCase =
        BookmarkedPostsUseCase(myPageRepository)

    @Provides
    @Singleton
    fun providesExchangeUseCase(paymentRepository: PaymentRepository): ExchangeUseCase =
        ExchangeUseCase(paymentRepository)

    @Provides
    @Singleton
    fun providesTeacherPointUseCase(paymentRepository: PaymentRepository) :TeacherPointUseCase =
        TeacherPointUseCase(paymentRepository)

    @Provides
    @Singleton
    fun providesTeacherDetailProfileUseCase(memberRepository: MemberRepository): TeacherDetailProfileUseCase =
        TeacherDetailProfileUseCase(memberRepository)

    @Provides
    @Singleton
    fun providesModifyTeacherProfile(memberRepository: MemberRepository): ModifyTeacherProfileUseCase =
        ModifyTeacherProfileUseCase(memberRepository)

    @Provides
    @Singleton
    fun providesModifyBossProfile(memberRepository: MemberRepository): ModifyBossProfileUseCase =
        ModifyBossProfileUseCase(memberRepository)

}