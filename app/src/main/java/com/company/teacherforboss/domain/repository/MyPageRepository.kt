package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.auth.AccountEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageCommentedPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageMyPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageMyQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPagePostsResponseEntity

interface MyPageRepository {
    suspend fun getAnsweredQuestion(myPageAnsweredQuestionRequestEntity: MyPageAnsweredQuestionRequestEntity):MyPageAnsweredQuestionResponseEntity

    suspend fun getMyQuestion(myPageMyQuestionRequestEntity: MyPageMyQuestionRequestEntity):MyPageAnsweredQuestionResponseEntity

    suspend fun getBookmarkedQuestions(): BookmarkedQuestionsResponseEntity

    suspend fun getCommentedPosts (myPageCommentedPostsRequestEntity: MyPageCommentedPostsRequestEntity): MyPagePostsResponseEntity

    suspend fun getMyPosts (myPageMyPostsRequestEntity: MyPageMyPostsRequestEntity): MyPagePostsResponseEntity



}