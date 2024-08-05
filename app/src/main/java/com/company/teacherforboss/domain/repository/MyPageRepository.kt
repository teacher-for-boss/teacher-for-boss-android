package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.mypage.AnsweredQuestionEntity
import com.company.teacherforboss.domain.model.mypage.AnsweredQuestionResponseEntity

interface MyPageRepository {
    suspend fun getAnsweredQuestion(): AnsweredQuestionResponseEntity

}