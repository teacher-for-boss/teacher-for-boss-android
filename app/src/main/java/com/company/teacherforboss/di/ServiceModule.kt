package com.company.teacherforboss.di

import com.company.teacherforboss.data.service.AuthService
import com.company.teacherforboss.data.service.CommunityService
import com.company.teacherforboss.data.service.HomeService
import com.company.teacherforboss.data.service.MemberService
import com.company.teacherforboss.data.service.MyPageService
import com.company.teacherforboss.data.service.PaymentService
import com.company.teacherforboss.data.service.SignupService
import com.company.teacherforboss.data.service.awsService
import com.company.teacherforboss.di.qualifier.Anonymous
import com.company.teacherforboss.di.qualifier.Auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesSignupService(@Anonymous retrofit: Retrofit): SignupService =
        retrofit.create(SignupService::class.java)

    @Provides
    @Singleton
    fun providesAwsService(@Anonymous retrofit: Retrofit):awsService=
        retrofit.create(awsService::class.java)

    @Provides
    @Singleton
    fun providesCommunitySerivce(@Auth retrofit: Retrofit):CommunityService=
        retrofit.create(CommunityService::class.java)

    @Provides
    @Singleton

    fun providesHomeService(@Auth retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun providesAuthSerivce(@Auth retrofit: Retrofit):AuthService=
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesMemberSerivce(@Auth retrofit: Retrofit):MemberService=
        retrofit.create(MemberService::class.java)

    @Provides
    @Singleton
    fun providesPaymentSerivce(@Auth retrofit: Retrofit): PaymentService =
        retrofit.create(PaymentService::class.java)

    fun providesMyPageSerivce(@Auth retrofit: Retrofit): MyPageService =
        retrofit.create(MyPageService::class.java)

}