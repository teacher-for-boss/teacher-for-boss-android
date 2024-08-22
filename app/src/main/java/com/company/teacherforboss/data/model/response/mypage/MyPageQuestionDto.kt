package com.company.teacherforboss.data.model.response.mypage

import com.company.teacherforboss.domain.model.mypage.MyPageQuestionEntity
import com.google.gson.annotations.SerializedName

data class MyPageQuestionDto(
    @SerializedName("questionId")
    val questionId: Long,
    @SerializedName("category")
    val category: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("solved")
    val solved: Boolean,
    @SerializedName("selectedTeacher")
    val selectedTeacher: String?,
    @SerializedName("createdAt")
    val createdAt: String
)
{
    fun toMyPageQuestionEntity()=MyPageQuestionEntity(
        questionId = questionId,
        category = category,
        title = title,
        content = content,
        solved = solved,
        selectedTeacher = selectedTeacher,
        createdAt = createdAt
    )
}