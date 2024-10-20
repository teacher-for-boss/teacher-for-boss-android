package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentDto
import com.company.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentLikeDto
import com.company.teacherforboss.data.model.request.community.boss.RequestBossTalkDto
import com.company.teacherforboss.data.model.request.community.boss.RequestBossTalkPostsDto
import com.company.teacherforboss.data.model.request.community.boss.RequestBossUploadPostDto
import com.company.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkQuestionsDto
import com.company.teacherforboss.data.model.request.community.teacher.RequestTeacherAnswerPostDto
import com.company.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkAnsDto

import com.company.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkAnswerDto
import com.company.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkAnswerLikeDto
import com.company.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkDto
import com.company.teacherforboss.data.model.request.community.teacher.RequestTeacherUploadPostDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseBossCommentDeleteDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseBossModifyDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseBossTalkBookmarkDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentListDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentLikeDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseBossTalkDeletePostDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseBossTalkLikeDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseBossUploadPostDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkQuestionsDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherAnswerListDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherAnswerModifyDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherAnswerPostDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherDeleteDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherModifyDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseTeacherTalkAnsDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherSelectDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkAnswerLikeDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkBodyDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkBookmarkDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkLikeDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherUploadPostDto
import com.company.teacherforboss.util.base.BaseResponse

interface CommunityRemoteDataSource {
    suspend fun getBossTalkPosts(requestBossTalkPostsDto: RequestBossTalkPostsDto):BaseResponse<ResponseBossTalkPostsDto>

    suspend fun getTeacherTalkQuestions(requestTeacherTalkQuestionsDto: RequestTeacherTalkQuestionsDto): BaseResponse<ResponseTeacherTalkQuestionsDto>

    suspend fun searchKeywordBossTalk(requestBossTalkPostsDto: RequestBossTalkPostsDto):BaseResponse<ResponseBossTalkPostsDto>

    suspend fun uploadBossTalkPost(requesetBossUploadPostDto: RequestBossUploadPostDto):BaseResponse<ResponseBossUploadPostDto>

    suspend fun getBossTalkBookmark(requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkBookmarkDto>

    suspend fun getBossTalkLike(requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkLikeDto>

    suspend fun getBossTalkBody(requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkBodyDto>

    suspend fun getBossTalkCommentList(requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkCommentListDto>

    suspend fun postBossTalkComment(requestBossTalkCommentDto: RequestBossTalkCommentDto, requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkCommentDto>

    suspend fun modifyBossTalkBody(requestBossTalkDto: RequestBossTalkDto,requestBossUploadPostDto: RequestBossUploadPostDto):BaseResponse<ResponseBossModifyDto>

    suspend fun deleteBossTalkPost(requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkDeletePostDto>

    suspend fun postBossTalkCommentLike(requestBossTalkCommentLikeDto: RequestBossTalkCommentLikeDto):BaseResponse<ResponseBossTalkCommentLikeDto>

    suspend fun postBossTalkCommentdisLike(requestBossTalkCommentLikeDto: RequestBossTalkCommentLikeDto):BaseResponse<ResponseBossTalkCommentLikeDto>

    suspend fun deleteBossTalkComment(requestBossTalkCommentLikeDto: RequestBossTalkCommentLikeDto): BaseResponse<ResponseBossCommentDeleteDto>

    //TeacherTalk
    suspend fun uploadTeacherTalkPost(requestTeacherUploadPostDto: RequestTeacherUploadPostDto): BaseResponse<ResponseTeacherUploadPostDto>

    suspend fun modifyTeacherTalkBody(requestTeacherTalkDto: RequestTeacherTalkDto, requestTeacherUploadPostDto: RequestTeacherUploadPostDto): BaseResponse<ResponseTeacherModifyDto>

    suspend fun getTeacherTalkBookmark(requestTeacherTalkDto: RequestTeacherTalkDto):BaseResponse<ResponseTeacherTalkBookmarkDto>

    suspend fun getTeacherTalkLike(requestTeacherTalkDto: RequestTeacherTalkDto):BaseResponse<ResponseTeacherTalkLikeDto>

    suspend fun getTeacherTalkBody(requestTeacherTalkDto: RequestTeacherTalkDto):BaseResponse<ResponseTeacherTalkBodyDto>

    suspend fun deleteTeacherTalkAns(requestTeacherTalkAnsDto: RequestTeacherTalkAnsDto):BaseResponse<ResponseTeacherTalkAnsDto>

    suspend fun deleteTeacherTalkBody(requestTeacherTalkDto: RequestTeacherTalkDto): BaseResponse<ResponseTeacherDeleteDto>

    suspend fun getTeacherTalkAnswerList(requestTeacherTalkDto: RequestTeacherTalkDto): BaseResponse<ResponseTeacherAnswerListDto>

    suspend fun postTeacherTalkAnswer(requestTeacherTalkDto: RequestTeacherTalkDto, requestTeacherAnswerPostDto: RequestTeacherAnswerPostDto): BaseResponse<ResponseTeacherAnswerPostDto>

    suspend fun modifyTeacherTalkAnswer(requestTeacherTalkDto: RequestTeacherTalkDto, requestTeacherTalkAnswerDto: RequestTeacherTalkAnswerDto, requestTeacherAnswerPostDto: RequestTeacherAnswerPostDto): BaseResponse<ResponseTeacherAnswerModifyDto>

    suspend fun selectTeacherTalkAnswer(requestTeacherTalkDto: RequestTeacherTalkDto, requestTeacherTalkAnswerDto: RequestTeacherTalkAnswerDto): BaseResponse<ResponseTeacherSelectDto>

    suspend fun postTeacherTalkAnswerLike(requestTeacherTalkAnswerLikeDto: RequestTeacherTalkAnswerLikeDto):BaseResponse<ResponseTeacherTalkAnswerLikeDto>

    suspend fun postTeacherTalkAnswerDislike(requestTeacherTalkAnswerLikeDto: RequestTeacherTalkAnswerLikeDto):BaseResponse<ResponseTeacherTalkAnswerLikeDto>

    suspend fun searchKeywordTeacherTalk(requestTeacherTalkQuestionsDto: RequestTeacherTalkQuestionsDto): BaseResponse<ResponseTeacherTalkQuestionsDto>

}