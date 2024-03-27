package com.example.teacherforboss.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="category_table")
data class CategoryEntity(
    @PrimaryKey
    val examCategoryId:Int=0,
    val categoryName:String=""
)
