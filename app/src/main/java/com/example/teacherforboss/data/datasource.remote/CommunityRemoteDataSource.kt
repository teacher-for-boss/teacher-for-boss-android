package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkPostsDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossUploadPostDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossModifyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBookmarkDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentListDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkLikeDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossUploadPostDto
import com.example.teacherforboss.util.base.BaseResponse

interface CommunityRemoteDataSource {
    suspend fun getBossTalkPosts(requestBossTalkPostsDto: RequestBossTalkPostsDto):BaseResponse<ResponseBossTalkPostsDto>

    suspend fun searchKeywordBossTalk(requestBossTalkPostsDto: RequestBossTalkPostsDto):BaseResponse<ResponseBossTalkPostsDto>

    suspend fun uploadBossTalkPost(requesetBossUploadPostDto: RequestBossUploadPostDto):BaseResponse<ResponseBossUploadPostDto>

    suspend fun getBossTalkBookmark(requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkBookmarkDto>

    suspend fun getBossTalkLike(requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkLikeDto>

    suspend fun getBossTalkBody(requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkBodyDto>

    suspend fun getBossTalkCommentList(requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkCommentListDto>

    suspend fun postBossTalkComment(requestBossTalkCommentDto: RequestBossTalkCommentDto, requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkCommentDto>

    suspend fun getTeacherTalkBookmark(requestTeacherTalkDto: RequestTeacherTalkDto):BaseResponse<ResponseTeacherTalkBookmarkDto>

    suspend fun getTeacherTalkLike(requestTeacherTalkDto: RequestTeacherTalkDto):BaseResponse<ResponseTeacherTalkLikeDto>

    suspend fun getTeacherTalkBody(requestTeacherTalkDto: RequestTeacherTalkDto):BaseResponse<ResponseTeacherTalkBodyDto>

    suspend fun modifyBossTalkBody(requestBossTalkDto: RequestBossTalkDto,requestBossUploadPostDto: RequestBossUploadPostDto):BaseResponse<ResponseBossModifyDto>


    //TeacherTalk
    suspend fun uploadTeacherTalkPost(requestTeacherUploadPostDto: RequestTeacherUploadPostDto): BaseResponse<ResponseTeacherUploadPostDto>
}