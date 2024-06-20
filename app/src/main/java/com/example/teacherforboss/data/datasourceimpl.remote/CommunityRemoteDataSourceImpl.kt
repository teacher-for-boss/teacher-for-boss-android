package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.CommunityRemoteDataSource
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkPostsDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossUploadPostDto
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

}