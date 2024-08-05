package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.response.mypage.AnsweredQuestionDto
import com.company.teacherforboss.data.model.response.mypage.ResponseAnsweredQuestionDto
import java.io.Serializable

data class AnsweredQuestionResponseEntity (
    val hasNext:Boolean,
    val answeredQuestionList: ArrayList<AnsweredQuestionEntity>
){
    fun toAnsweredTeacherResponseDto() = ResponseAnsweredQuestionDto(
        hasNext=hasNext,
        answeredQuestionList = answeredQuestionList.mapTo(ArrayList()) { it.toAnsweredQuestionDto() }
    )
}

data class AnsweredQuestionEntity(
    val questionId:Long,
    val category: String,
    val title: String,
    val content: String,
    val solved: Boolean,
    val selectedTeacher:String,
    val createdAt: String,
): Serializable {
    fun toAnsweredQuestionDto()=AnsweredQuestionDto(
        questionId=questionId,
        category=category,
        title=title,
        content=content,
        solved=solved,
        selectedTeacher=selectedTeacher,
        createdAt=createdAt
    )
}