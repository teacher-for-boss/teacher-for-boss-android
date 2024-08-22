package com.company.teacherforboss.di

import android.content.Context
import com.company.teacherforboss.util.base.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(@ApplicationContext context: Context): LocalDataSource {
        return LocalDataSource(context)
    }
}