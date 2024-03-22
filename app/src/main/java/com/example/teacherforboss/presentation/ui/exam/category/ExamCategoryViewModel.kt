package com.example.teacherforboss.presentation.ui.exam.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.exams.ExamCategoryEntity
import com.example.teacherforboss.domain.usecase.ExamCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamCategoryViewModel @Inject constructor(
    private val examCategoryUseCase: ExamCategoryUseCase
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