package com.example.teacherforboss.di

import com.example.teacherforboss.data.datasource.remote.AwsRemoteDataSource
import com.example.teacherforboss.data.datasource.remote.CommunityRemoteDataSource
import com.example.teacherforboss.data.datasource.remote.HomeRemoteDataSource
import com.example.teacherforboss.data.datasource.remote.SignupRemoteDataSource
import com.example.teacherforboss.data.datasourceimpl.remote.AwsRemoteDataSourceImpl
import com.example.teacherforboss.data.datasourceimpl.remote.CommunityRemoteDataSourceImpl
import com.example.teacherforboss.data.datasourceimpl.remote.HomeRemoteDataSourceImpl
import com.example.teacherforboss.data.datasourceimpl.remote.SignupRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsSignupRemoteDataSource(signupRemoteDataSourceImpl: SignupRemoteDataSourceImpl):SignupRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsAwsRemoteDataSource(awsRemoteDataSourceImpl: AwsRemoteDataSourceImpl):AwsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsCommunityRemoteDataSource(communityRemoteDataSourceImpl: CommunityRemoteDataSourceImpl):CommunityRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsHomeRemoteDataSource(homeRemoteDataSourceImpl: HomeRemoteDataSourceImpl): HomeRemoteDataSource

}
