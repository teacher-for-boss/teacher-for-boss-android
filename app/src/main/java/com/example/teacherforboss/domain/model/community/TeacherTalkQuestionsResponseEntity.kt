package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.response.community.teacher.QuestionDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkQuestionsDto

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
){
    fun toQuestionDto()=QuestionDto(
        questionId=questionId,
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