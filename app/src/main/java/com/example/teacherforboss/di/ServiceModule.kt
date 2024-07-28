package com.example.teacherforboss.di

import com.example.teacherforboss.data.service.AuthService
import com.example.teacherforboss.data.service.CommunityService
import com.example.teacherforboss.data.service.HomeService
import com.example.teacherforboss.data.service.MemberService
import com.example.teacherforboss.data.service.PaymentService
import com.example.teacherforboss.data.service.SignupService
import com.example.teacherforboss.data.service.awsService
import com.example.teacherforboss.di.qualifier.Anonymous
import com.example.teacherforboss.di.qualifier.Auth
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

}