package com.example.teacherforboss.di

import com.example.teacherforboss.data.repository.MembersRepositoryImpl
import com.example.teacherforboss.domain.repository.MembersRepository
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
    abstract fun bindsMembersRepository(membersRepositoryImpl: MembersRepositoryImpl): MembersRepository
}