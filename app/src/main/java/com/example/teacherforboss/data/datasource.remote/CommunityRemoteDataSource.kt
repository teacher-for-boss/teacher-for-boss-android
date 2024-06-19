package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkPostsDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBookmarkDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkLikeDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto
import com.example.teacherforboss.util.base.BaseResponse

interface CommunityRemoteDataSource {
    suspend fun getBossTalkPosts(requestBossTalkPostsDto: RequestBossTalkPostsDto):BaseResponse<ResponseBossTalkPostsDto>

    suspend fun searchKeywordBossTalk(requestBossTalkPostsDto: RequestBossTalkPostsDto):BaseResponse<ResponseBossTalkPostsDto>

    suspend fun getBossTalkBookmark(requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkBookmarkDto>

    suspend fun getBossTalkLike(requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkLikeDto>

    suspend fun getBossTalkBody(requestBossTalkDto: RequestBossTalkDto):BaseResponse<ResponseBossTalkBodyDto>

}