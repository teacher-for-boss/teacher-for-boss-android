package com.company.teacherforboss.data.model.response.mypage

import com.company.teacherforboss.domain.model.mypage.AnsweredQuestionEntity
import com.company.teacherforboss.domain.model.mypage.AnsweredQuestionResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseAnsweredQuestionDto (
    @SerializedName("hasNext")
    val hasNext:Boolean,
    @SerializedName("answeredQuestionList")
    val answeredQuestionList: ArrayList<AnsweredQuestionDto>
){
    fun toAnsweredQuestionEntity(): AnsweredQuestionResponseEntity {
        val questionEntities = answeredQuestionList.mapTo(ArrayList()){ it.toAnsweredQuestionEntity() }
        return AnsweredQuestionResponseEntity(
            hasNext=hasNext,
            answeredQuestionList=questionEntities
        )
    }
}

data class AnsweredQuestionDto(
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
    val selectedTeacher: String,
    @SerializedName("createdAt")
    val createdAt: String
){
    fun toAnsweredQuestionEntity() = AnsweredQuestionEntity(
        questionId=questionId,
        category=category,
        title=title,
        content=content,
        solved=solved,
        selectedTeacher=selectedTeacher,
        createdAt=createdAt
    )
}