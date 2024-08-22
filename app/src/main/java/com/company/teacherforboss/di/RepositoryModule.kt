package com.company.teacherforboss.di

import com.company.teacherforboss.data.repositoryImpl.AuthRepositoryImpl
import com.company.teacherforboss.data.repositoryImpl.AwsRepositoryImpl
import com.company.teacherforboss.data.repositoryImpl.CommunityRepositoryImpl
import com.company.teacherforboss.data.repositoryImpl.HomeRepositoryImpl
import com.company.teacherforboss.data.repositoryImpl.MemberRepositoryImpl
import com.company.teacherforboss.data.repositoryImpl.MyPageRepositoryImpl
import com.company.teacherforboss.data.repositoryImpl.PaymentRepositoryImpl
import com.company.teacherforboss.data.repositoryImpl.SignupRepositoryImpl
import com.company.teacherforboss.data.repositoryImpl.UserRepositoryImpl
import com.company.teacherforboss.domain.repository.AuthRepository
import com.company.teacherforboss.domain.repository.AwsReository
import com.company.teacherforboss.domain.repository.CommunityRepository
import com.company.teacherforboss.domain.repository.HomeRepository
import com.company.teacherforboss.domain.repository.MemberRepository
import com.company.teacherforboss.domain.repository.MyPageRepository
import com.company.teacherforboss.domain.repository.PaymentRepository
import com.company.teacherforboss.domain.repository.SignupRepository
import com.company.teacherforboss.domain.repository.UserRepository
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

    @Binds
    @Singleton
    abstract fun bindsAwsRepository(awsRepositoryImpl: AwsRepositoryImpl) : AwsReository

    @Binds
    @Singleton
    abstract fun bindsCommunityRepository(communityRepositoryImpl: CommunityRepositoryImpl):CommunityRepository

    @Binds
    @Singleton
    abstract fun bindsHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
  
    @Binds
    @Singleton
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl):AuthRepository

    @Binds
    @Singleton
    abstract fun bindMemberRepository(memberRepositoryImpl: MemberRepositoryImpl):MemberRepository

    @Binds
    @Singleton
    abstract fun bindPaymentRepository(paymentRepositoryImpl: PaymentRepositoryImpl): PaymentRepository

    @Binds
    @Singleton
    abstract fun bindMyPageRepository(myPageRepositoryImpl: MyPageRepositoryImpl): MyPageRepository

}