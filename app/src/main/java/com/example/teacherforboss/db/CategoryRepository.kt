package com.example.teacherforboss.db

import com.example.teacherforboss.db.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun insertCategory(categoryEntity: CategoryEntity)

    suspend fun getCategoryList(): List<CategoryEntity>

    suspend fun getCategoryNameList(): List<String>
}