package com.example.teacherforboss.db

import android.content.Context


interface AppContainer{
    val categoryRepository:CategoryRepository
}
class AppDataContainer(private val context: Context):AppContainer {
    override val categoryRepository: CategoryRepository by lazy {
        CategoryRepositoryImpl(CategoryDB.getDatabase(context).categoryDao())
    }
}
