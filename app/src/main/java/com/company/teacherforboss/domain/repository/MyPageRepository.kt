package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsResponseEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsRequestEntity
import com.company.teacherforboss.domain.model.auth.AccountEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsResponseEntity
import com.company.teacherforboss.domain.model.mypage.ChipInfoResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageCommentedPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageMyPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageMyQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPagePostsResponseEntity

interface MyPageRepository {
    suspend fun getBookmarkedQuestions(bookmarkedQuestionsRequestEntity: BookmarkedQuestionsRequestEntity): BookmarkedQuestionsResponseEntity
    suspend fun getBookmarkedPosts(bookmarkedPostsRequestEntity: BookmarkedPostsRequestEntity): BookmarkedPostsResponseEntity
    suspend fun getAnsweredQuestion(myPageAnsweredQuestionRequestEntity: MyPageAnsweredQuestionRequestEntity):MyPageAnsweredQuestionResponseEntity
    suspend fun getChipInfo(): Result<ChipInfoResponseEntity>
    suspend fun getMyQuestion(myPageMyQuestionRequestEntity: MyPageMyQuestionRequestEntity):MyPageAnsweredQuestionResponseEntity
    suspend fun getCommentedPosts (myPageCommentedPostsRequestEntity: MyPageCommentedPostsRequestEntity): MyPagePostsResponseEntity
    suspend fun getMyPosts (myPageMyPostsRequestEntity: MyPageMyPostsRequestEntity): MyPagePostsResponseEntity
}