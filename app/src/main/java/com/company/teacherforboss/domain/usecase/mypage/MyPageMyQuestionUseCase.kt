package com.company.teacherforboss.domain.usecase.mypage

import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageMyQuestionRequestEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.company.teacherforboss.domain.repository.MyPageRepository

class MyPageMyQuestionUseCase(private val myPageRepository: MyPageRepository) {
    suspend operator fun invoke(myPageMyQuestionRequestEntity: MyPageMyQuestionRequestEntity) : MyPageAnsweredQuestionResponseEntity
            = myPageRepository.getMyQuestion(myPageMyQuestionRequestEntity)
}