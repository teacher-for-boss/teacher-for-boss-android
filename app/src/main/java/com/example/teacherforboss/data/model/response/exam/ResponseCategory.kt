package com.example.teacherforboss.data.model.response.exam

import com.example.teacherforboss.domain.model.exams.ExamCategoryEntity
import com.google.gson.annotations.SerializedName

class ResponseCategory (
    @SerializedName("examCategoryList")
    val examCategoryList: List<ExamCategory>
) {
    data class ExamCategory (
        @SerializedName("examCategoryId")
        val examCategoryId: Int,

        @SerializedName("categoryName")
        val categoryName: String
    )

    fun toExamCategoryEntity()= ExamCategoryEntity (examCategoryList = examCategoryList)
}