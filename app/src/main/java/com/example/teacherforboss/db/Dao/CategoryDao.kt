package com.example.teacherforboss.db.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.teacherforboss.db.entity.CategoryEntity
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert
    fun insertCategory(categoryEntity: CategoryEntity)
    @Query("SELECT *FROM category_table")
    fun getCategoryList(): List<CategoryEntity>

    @Query("Select categoryName From category_table")
    fun getCategoryNameList():List<String>
}