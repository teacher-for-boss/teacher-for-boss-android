package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.db.CategoryRepository
import com.example.teacherforboss.db.entity.CategoryEntity

class CategoryUseCase(
    private val categoryRepository: CategoryRepository
){
    suspend operator fun invoke():List<CategoryEntity> = categoryRepository.getCategoryList()
}