package com.company.teacherforboss.data.model.response.mypage

import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBookmarkedQuestionsDto (
    @SerializedName("hasNext")
    val hasNext:Boolean,
    @SerializedName("questionList")
    val bookmarkedQuestionsList: ArrayList<BookmarkedQuestionsDto>
){
    fun toBookmarkedQuestionsEntity(): BookmarkedQuestionsResponseEntity {
        val questionEntities = bookmarkedQuestionsList.mapTo(ArrayList()){ it.toBookmarkedQuestionsEntity() }
        return BookmarkedQuestionsResponseEntity(
            hasNext=hasNext,
            bookmarkedQuestionsList=questionEntities
        )
    }
}

data class BookmarkedQuestionsDto(
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
){
    fun toBookmarkedQuestionsEntity() = BookmarkedQuestionsEntity(
        questionId=questionId,
        category=category,
        title=title,
        content=content,
        solved=solved,
        selectedTeacher=selectedTeacher,
        createdAt=createdAt
    )
}