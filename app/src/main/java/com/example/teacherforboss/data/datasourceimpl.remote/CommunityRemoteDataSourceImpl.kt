package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.CommunityRemoteDataSource
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentLikeDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkPostsDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossUploadPostDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossModifyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBookmarkDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentListDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentLikeDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkLikeDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossUploadPostDto
import com.example.teacherforboss.data.service.CommunityService
import com.example.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class CommunityRemoteDataSourceImpl @Inject constructor(
    private val communityService: CommunityService
) :CommunityRemoteDataSource{
    override suspend fun getBossTalkPosts(requestBossTalkPostsDto: RequestBossTalkPostsDto): BaseResponse<ResponseBossTalkPostsDto>
    =communityService.getBossTalkPosts(lastPostId = requestBossTalkPostsDto.lastPostId, size = requestBossTalkPostsDto.size, sortBy = requestBossTalkPostsDto.sortBy?:"latest")

    override suspend fun searchKeywordBossTalk(requestBossTalkPostsDto: RequestBossTalkPostsDto): BaseResponse<ResponseBossTalkPostsDto>
    =communityService.searchKeywordBossTalk(lastPostId = requestBossTalkPostsDto.lastPostId,size=requestBossTalkPostsDto.size, keyword = requestBossTalkPostsDto.keyword?:"")

    override suspend fun uploadBossTalkPost(requesetBossUploadPostDto: RequestBossUploadPostDto): BaseResponse<ResponseBossUploadPostDto>
    =communityService.uploadPost(requesetBossUploadPostDto)
    override suspend fun getBossTalkBookmark(requestBossTalkDto: RequestBossTalkDto): BaseResponse<ResponseBossTalkBookmarkDto>
    =communityService.getBossTalkBookmark(postId = requestBossTalkDto.postId)

    override suspend fun getBossTalkLike(requestBossTalkDto: RequestBossTalkDto): BaseResponse<ResponseBossTalkLikeDto>
    =communityService.getBossTalkLike(postId = requestBossTalkDto.postId)

    override suspend fun getBossTalkBody(requestBossTalkDto: RequestBossTalkDto): BaseResponse<ResponseBossTalkBodyDto>
    =communityService.getBossTalkBody(postId = requestBossTalkDto.postId)

    override suspend fun modifyBossTalkBody(requestBossTalkDto: RequestBossTalkDto, requestBossUploadPostDto: RequestBossUploadPostDto): BaseResponse<ResponseBossModifyDto>
    =communityService.modifyBossTalkBody(postId = requestBossTalkDto.postId,requestBossUploadPostDto=requestBossUploadPostDto)

    override suspend fun postBossTalkCommentLike(requestBossTalkCommentLikeDto: RequestBossTalkCommentLikeDto): BaseResponse<ResponseBossTalkCommentLikeDto>
    =communityService.likeBossTalkComment(postId = requestBossTalkCommentLikeDto.postId, commentId = requestBossTalkCommentLikeDto.commentId)


    override suspend fun postBossTalkCommentdisLike(requestBossTalkCommentLikeDto: RequestBossTalkCommentLikeDto): BaseResponse<ResponseBossTalkCommentLikeDto>
    =communityService.dislikeBossTalkComment(postId = requestBossTalkCommentLikeDto.postId, commentId = requestBossTalkCommentLikeDto.commentId)


    override suspend fun getBossTalkCommentList(requestBossTalkDto: RequestBossTalkDto): BaseResponse<ResponseBossTalkCommentListDto>
    = communityService.getBossTalkCommentList(postId = requestBossTalkDto.postId)

    override suspend fun postBossTalkComment(
        requestBossTalkCommentDto: RequestBossTalkCommentDto,
        requestBossTalkDto: RequestBossTalkDto
    ): BaseResponse<ResponseBossTalkCommentDto> = communityService.postBossTalkComment(requestBossTalkCommentDto, postId = requestBossTalkDto.postId)

}