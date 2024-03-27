package com.example.teacherforboss.db

import com.example.teacherforboss.db.Dao.CategoryDao
import com.example.teacherforboss.db.entity.CategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
): CategoryRepository {
    override suspend fun insertCategory(categoryEntity: CategoryEntity){
        withContext(Dispatchers.IO){
            categoryDao.insertCategory(categoryEntity)
        }
    }

    override suspend fun getCategoryList(): List<CategoryEntity> {
        return withContext(Dispatchers.IO) {
            categoryDao.getCategoryList()
        }
    }
    override suspend fun getCategoryNameList(): List<String> {
        return withContext(Dispatchers.IO){
            categoryDao.getCategoryNameList()
        }
    }
}