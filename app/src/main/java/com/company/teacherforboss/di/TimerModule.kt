package com.company.teacherforboss.di

import com.company.teacherforboss.util.Timer.Custom10mTimer
import com.company.teacherforboss.util.Timer.Custom3mTimer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TimerModule {
    @Singleton
    @Provides
    fun provideCustom3Timer(): Custom3mTimer {
        return Custom3mTimer()
    }

    @Singleton
    @Provides
    fun provideCustom10Timer(): Custom10mTimer {
        return Custom10mTimer()
    }

}