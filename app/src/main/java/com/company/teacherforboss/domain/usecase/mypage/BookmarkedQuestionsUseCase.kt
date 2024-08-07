package com.company.teacherforboss.domain.usecase.mypage

import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsResponseEntity
import com.company.teacherforboss.domain.repository.MyPageRepository

class BookmarkedQuestionsUseCase(
    private val myPageRepository: MyPageRepository
) {
    suspend operator fun invoke(): BookmarkedQuestionsResponseEntity =
        myPageRepository.getBookmarkedQuestions()
}