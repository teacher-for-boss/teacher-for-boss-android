package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.community.BossTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentListResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkDeletePostResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkLikeResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkModifyPostResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkPostsRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkUploadPostRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkUploadPostResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkRequestEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkLikeResponseEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkRequestEntity

interface CommunityRepository {
    suspend fun getBossTalkPosts(bossTalkPostsRequestEntity: BossTalkPostsRequestEntity):BossTalkPostsResponseEntity

    suspend fun searchKeywordBossTalk(bossTalkPostsRequestEntity: BossTalkPostsRequestEntity):BossTalkPostsResponseEntity

    suspend fun uploadBossTalkPost(bossTalkUploadPostRequestEntity: BossTalkUploadPostRequestEntity):BossTalkUploadPostResponseEntity

    suspend fun getBossTalkBookmark(bossTalkRequestEntity: BossTalkRequestEntity):BossTalkBookmarkResponseEntity

    suspend fun getBossTalkLike(bossTalkRequestEntity: BossTalkRequestEntity):BossTalkLikeResponseEntity

    suspend fun getBossTalkBody(bossTalkRequestEntity: BossTalkRequestEntity):BossTalkBodyResponseEntity

    suspend fun modifyBossTalkBody(bossTalkRequestEntity: BossTalkRequestEntity,bossTalkUploadPostRequestEntity: BossTalkUploadPostRequestEntity):BossTalkModifyPostResponseEntity

    suspend fun getBossTalkCommentList(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkCommentListResponseEntity

    suspend fun postBossTalkComment(bossTalkCommentRequestEntity: BossTalkCommentRequestEntity, bossTalkRequestEntity: BossTalkRequestEntity): BossTalkCommentResponseEntity

    suspend fun deleteBossTalkPost(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkDeletePostResponseEntity

    suspend fun getTeacherTalkBody(teacherTalkRequestEntity: TeacherTalkRequestEntity):TeacherTalkBodyResponseEntity

    suspend fun getTeacherTalkLike(teacherTalkRequestEntity: TeacherTalkRequestEntity):TeacherTalkLikeResponseEntity

    suspend fun getTeacherTalkBookmark(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkBookmarkResponseEntity
}