package com.example.teacherforboss.presentation.ui.exam

import androidx.lifecycle.ViewModel
import com.example.teacherforboss.data.model.response.exam.ResponseCategoryDetailDto

class ExamCategoryViewModel: ViewModel() {
    val dummy_category_detailList= listOf(
        ResponseCategoryDetailDto.categoryDetailDto("detail1",1),
        ResponseCategoryDetailDto.categoryDetailDto("detail2",2),
        ResponseCategoryDetailDto.categoryDetailDto("detail3",3),
        ResponseCategoryDetailDto.categoryDetailDto("detail4",4),
        ResponseCategoryDetailDto.categoryDetailDto("detail5",5),
    )
}