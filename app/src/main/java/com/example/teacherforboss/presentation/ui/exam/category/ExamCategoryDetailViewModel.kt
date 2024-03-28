package com.example.teacherforboss.presentation.ui.exam.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.data.model.response.exam.ResponseCategory
import com.example.teacherforboss.data.model.response.exam.ResponseCategoryDetailDto
import com.example.teacherforboss.db.AppDataContainer
import com.example.teacherforboss.db.CategoryRepository
import com.example.teacherforboss.db.entity.CategoryEntity
import com.example.teacherforboss.domain.usecase.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamCategoryDetailViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
):ViewModel(){

    val dummyCategoryList= listOf<CategoryEntity>(
        CategoryEntity(categoryName = "마케팅", examCategoryId =1 ),
        CategoryEntity(categoryName = "위생", examCategoryId =2 ),
        CategoryEntity(categoryName = "상권", examCategoryId =3 ),
        CategoryEntity(categoryName = "운영", examCategoryId =4 ),
        CategoryEntity(categoryName = "서비스", examCategoryId =5 ),
        CategoryEntity(categoryName = "직원관리", examCategoryId =6 ),
        CategoryEntity(categoryName = "인테리어", examCategoryId =7 ),
        CategoryEntity(categoryName = "정책", examCategoryId =8 ),
        CategoryEntity(categoryName = "메뉴", examCategoryId =9 ),
        CategoryEntity(categoryName = "아이디어", examCategoryId =10 )

    )


    val dummy_category_detailList= listOf(
        ResponseCategoryDetailDto.categoryDetailDto("detail1",1),
        ResponseCategoryDetailDto.categoryDetailDto("detail2",2),
        ResponseCategoryDetailDto.categoryDetailDto("detail3",3),
        ResponseCategoryDetailDto.categoryDetailDto("detail4",4),
        ResponseCategoryDetailDto.categoryDetailDto("detail5",5),
    )
}