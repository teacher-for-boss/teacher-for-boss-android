package com.company.teacherforboss.domain.usecase.mypage

import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsResponseEntity
import com.company.teacherforboss.domain.repository.MyPageRepository

class BookmarkedPostsUseCase (
    private val myPageRepository: MyPageRepository
){
    suspend operator fun invoke(bookmarkedPostsRequestEntity: BookmarkedPostsRequestEntity): BookmarkedPostsResponseEntity =
        myPageRepository.getBookmarkedPosts(bookmarkedPostsRequestEntity)
}