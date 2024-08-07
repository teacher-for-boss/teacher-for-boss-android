package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.auth.AccountEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsResponseEntity

interface MyPageRepository {
    suspend fun getAnsweredQuestion(myPageAnsweredQuestionRequestEntity: MyPageAnsweredQuestionRequestEntity):MyPageAnsweredQuestionResponseEntity

    suspend fun getBookmarkedQuestions(): BookmarkedQuestionsResponseEntity


}