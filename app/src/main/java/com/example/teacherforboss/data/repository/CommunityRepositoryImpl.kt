package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.CommunityRemoteDataSource
import com.example.teacherforboss.domain.model.community.BossTalkPostsRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityDataSource: CommunityRemoteDataSource
) :CommunityRepository{
    override suspend fun getBossTalkPosts(bossTalkPostsRequestEntity: BossTalkPostsRequestEntity): BossTalkPostsResponseEntity {
        return runCatching {
            communityDataSource.getBossTalkPosts(
                requestBossTalkPostsDto = bossTalkPostsRequestEntity.toRequestBossTalkPostsDto()
            ).result.toBossTalkPostsEntity()
        }.getOrElse { err->
            throw err
        }
    }

    override suspend fun searchKeywordBossTalk(bossTalkPostsRequestEntity: BossTalkPostsRequestEntity): BossTalkPostsResponseEntity {
        return runCatching {
            communityDataSource.searchKeywordBossTalk(requestBossTalkPostsDto=bossTalkPostsRequestEntity.toRequestBossTalkPostsDto())
                .result.toBossTalkPostsEntity()
        }.getOrElse { err->throw err }
    }

}