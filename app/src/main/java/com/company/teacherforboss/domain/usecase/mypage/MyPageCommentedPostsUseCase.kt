package com.company.teacherforboss.domain.usecase.mypage

import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageCommentedPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageMyQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPagePostsResponseEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.company.teacherforboss.domain.repository.MyPageRepository

class MyPageCommentedPostsUseCase(private val myPageRepository: MyPageRepository) {
    suspend operator fun invoke(myPageCommentedPostsRequestEntity: MyPageCommentedPostsRequestEntity) : MyPagePostsResponseEntity
            = myPageRepository.getCommentedPosts(myPageCommentedPostsRequestEntity)
}