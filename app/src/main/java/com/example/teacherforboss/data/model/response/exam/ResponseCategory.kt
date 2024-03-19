package com.example.teacherforboss.data.model.response.exam

import com.example.teacherforboss.domain.model.exams.ExamCategoryEntity
import com.google.gson.annotations.SerializedName

class ResponseCategory (
    @SerializedName("code")
    var code:String,

    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var result: Result
) {
    data class Result (
        @SerializedName("examCategoryList")
        val examCategoryList: List<ExamCategory>
    )
    data class ExamCategory (
        @SerializedName("examCategoryId")
        val examCategoryId: Int,

        @SerializedName("categoryName")
        val categoryName: String
    )

    fun toExamCategoryEntity()= ExamCategoryEntity (examCategoryList = result.examCategoryList)
}