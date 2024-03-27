package com.example.teacherforboss.di

import android.content.Context
import androidx.room.Room
import com.example.teacherforboss.db.AppContainer
import com.example.teacherforboss.db.AppDataContainer
import com.example.teacherforboss.db.CategoryDB
import com.example.teacherforboss.db.CategoryRepository
import com.example.teacherforboss.db.CategoryRepositoryImpl
import com.example.teacherforboss.db.Dao.CategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideCategoryDao(db:CategoryDB):CategoryDao{
        return db.categoryDao()
    }

    @Provides
    fun bindsCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepositoryImpl(categoryDao)
    }

    @Provides
    @Singleton
    fun bindsAppDataContainer(@ApplicationContext context: Context): AppContainer{
        return AppDataContainer(context)
    }

    @Provides
    fun provideCategoryDb(@ApplicationContext appContext:Context):CategoryDB{
        return Room.databaseBuilder(
            appContext,
            CategoryDB::class.java,
            "CategoryDB"
        ).build()
    }
}