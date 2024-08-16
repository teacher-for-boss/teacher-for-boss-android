package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsRequestEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsResponseEntity

interface MyPageRepository {
    suspend fun getBookmarkedQuestions(bookmarkedQuestionsRequestEntity: BookmarkedQuestionsRequestEntity): BookmarkedQuestionsResponseEntity

}