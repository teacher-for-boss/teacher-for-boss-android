package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.community.BossTalkCommentListResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkLikeResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkCommentLikeRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkCommentLikeResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkCommentRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkCommentResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkLikeResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkModifyPostResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkPostsRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkUploadPostRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkUploadPostResponseEntity
import com.example.teacherforboss.domain.model.community.boss.TeacherAnswerPostResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherAnswerListResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherAnswerPostRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkDeleteResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkModifyResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherUploadPostRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherUploadPostResponseEntity

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

    suspend fun postBossTalkCommentLike(bossTalkCommentLikeRequestEntity: BossTalkCommentLikeRequestEntity): BossTalkCommentLikeResponseEntity

    suspend fun postBossTalkCommentdisLike(bossTalkCommentLikeRequestEntity: BossTalkCommentLikeRequestEntity):BossTalkCommentLikeResponseEntity

    suspend fun getTeacherTalkBody(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkBodyResponseEntity

    suspend fun getTeacherTalkLike(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkLikeResponseEntity

    suspend fun getTeacherTalkBookmark(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkBookmarkResponseEntity

    //TeacherTalk
    suspend fun uploadTeacherTalkPost(teacherUploadPostRequestEntity: TeacherUploadPostRequestEntity): TeacherUploadPostResponseEntity

    suspend fun modifyTeacherTalkBody(teacherTalkRequestEntity: TeacherTalkRequestEntity, teacherUploadPostRequestEntity: TeacherUploadPostRequestEntity): TeacherTalkModifyResponseEntity

    suspend fun deleteTeacherTalkBody(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkDeleteResponseEntity

    suspend fun getTeacherTalkAnswerList(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherAnswerListResponseEntity

    suspend fun postTeacherTalkAnswer(teacherTalkRequestEntity: TeacherTalkRequestEntity, teacherAnswerPostRequestEntity: TeacherAnswerPostRequestEntity): TeacherAnswerPostResponseEntity
}