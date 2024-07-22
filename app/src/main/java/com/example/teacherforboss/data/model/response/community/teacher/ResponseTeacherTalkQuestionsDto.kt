package com.example.teacherforboss.data.model.response.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.QuestionEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherTalkQuestionsDto (
    @SerializedName("hasNext")
    val hasNext:Boolean,
    @SerializedName("questionList")
    val questionList: ArrayList<QuestionDto>
){
    fun toTeacherTalkQuestionsEntity(): TeacherTalkQuestionsResponseEntity {
        val QuestionEntities = questionList.mapTo(ArrayList()) {it.toQuestionEntity()}
        return TeacherTalkQuestionsResponseEntity(
            questionList=QuestionEntities,
            hasNext = hasNext
        )
    }
}

data class QuestionDto(
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
    @SerializedName("bookmarkCount")
    val bookmarkCount: Int,
    @SerializedName("answerCount")
    val answerCount: Int,
    @SerializedName("likeCount")
    val likeCount: Int,
    @SerializedName("liked")
    val liked: Boolean,
    @SerializedName("bookmarked")
    val bookmarked: Boolean,
    @SerializedName("createdAt")
    val createdAt: String, //LocalDate인데가 string으로 해야 에러가 안뜸,,
){
    fun toQuestionEntity()= QuestionEntity(
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