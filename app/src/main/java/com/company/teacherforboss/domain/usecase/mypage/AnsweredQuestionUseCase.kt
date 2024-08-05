package com.company.teacherforboss.domain.usecase.mypage

import com.company.teacherforboss.domain.model.mypage.AnsweredQuestionEntity
import com.company.teacherforboss.domain.model.mypage.AnsweredQuestionResponseEntity
import com.company.teacherforboss.domain.repository.MyPageRepository

class AnsweredQuestionUseCase(
    private val myPageRepository: MyPageRepository
) {
    suspend operator fun invoke(): AnsweredQuestionResponseEntity =
        myPageRepository.getAnsweredQuestion()
}