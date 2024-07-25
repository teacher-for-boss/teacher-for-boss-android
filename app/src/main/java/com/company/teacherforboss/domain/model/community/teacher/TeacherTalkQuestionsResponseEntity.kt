package com.company.teacherforboss.domain.model.community.teacher

import com.company.teacherforboss.data.model.response.community.teacher.QuestionDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkQuestionsDto
import java.io.Serializable

data class TeacherTalkQuestionsResponseEntity (
    val hasNext: Boolean,
    val questionList: ArrayList<QuestionEntity>
){
    fun toTeacherTalkResponseDto()= ResponseTeacherTalkQuestionsDto(
        hasNext=hasNext,
        questionList=questionList.mapTo(java.util.ArrayList()) {it.toQuestionDto()}
    )
}

data class QuestionEntity(
    val questionId: Long,
    val category: String,
    val title: String,
    val content: String,
    val solved: Boolean,
    val selectedTeacher: String?,
    val bookmarkCount: Int,
    val answerCount: Int,
    val likeCount: Int,
    val liked: Boolean,
    val bookmarked: Boolean,
    val createdAt: String,
): Serializable {
    fun toQuestionDto()=QuestionDto(
        questionId=questionId,
        category = category,
        title=title,
        content=content,
        solved=solved,
        selectedTeacher=selectedTeacher,
        bookmarkCount=bookmarkCount,
        answerCount=answerCount,
        likeCount=likeCount,
        liked=liked,
        bookmarked=bookmarked,
        createdAt=createdAt,
    )
}