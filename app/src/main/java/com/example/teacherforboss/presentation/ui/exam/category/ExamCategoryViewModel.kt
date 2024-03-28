package com.example.teacherforboss.presentation.ui.exam.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.data.model.response.exam.ResponseCategory
import com.example.teacherforboss.data.model.response.exam.ResponseCategoryDetailDto
import com.example.teacherforboss.db.CategoryDB
import com.example.teacherforboss.db.CategoryRepository
import com.example.teacherforboss.db.entity.CategoryEntity
import com.example.teacherforboss.domain.model.exams.ExamCategoryEntity
import com.example.teacherforboss.domain.usecase.ExamCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamCategoryViewModel @Inject constructor(
    private val examCategoryUseCase: ExamCategoryUseCase,
    private val categoryRepository: CategoryRepository

) : ViewModel() {
    private val _categoryLiveData = MutableLiveData<ExamCategoryEntity>()
    val categoryLiveData: LiveData<ExamCategoryEntity> = _categoryLiveData

    suspend fun getCategory() {
        viewModelScope.launch {
            try {
                val categoryEntity = examCategoryUseCase()
                _categoryLiveData.value = categoryEntity

            } catch (ex: Exception) {}
        }
    }


}