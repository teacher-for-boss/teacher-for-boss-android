package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.response.mypage.BookmarkedQuestionsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto
import java.io.Serializable

data class BookmarkedQuestionsResponseEntity (
    val hasNext:Boolean,
    val bookmarkedQuestionsList: ArrayList<BookmarkedQuestionsEntity>
){
    fun toBookmarkedQuestionsResponseDto() = ResponseBookmarkedQuestionsDto(
        hasNext=hasNext,
        bookmarkedQuestionsList = bookmarkedQuestionsList.mapTo(ArrayList()) { it.toBookmarkedQuestionsDto() }
    )
}

data class BookmarkedQuestionsEntity(
    val questionId:Long,
    val category: String,
    val title: String,
    val content: String,
    val solved: Boolean,
    val selectedTeacher:String?,
    val createdAt: String,
): Serializable {
    fun toBookmarkedQuestionsDto() = BookmarkedQuestionsDto(
        questionId = questionId,
        category = category,
        title = title,
        content = content,
        solved = solved,
        selectedTeacher = selectedTeacher,
        createdAt = createdAt
    )
}