package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.auth.AccountEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity

interface MyPageRepository {
    suspend fun getAnsweredQuestion(myPageAnsweredQuestionRequestEntity: MyPageAnsweredQuestionRequestEntity):MyPageAnsweredQuestionResponseEntity

}