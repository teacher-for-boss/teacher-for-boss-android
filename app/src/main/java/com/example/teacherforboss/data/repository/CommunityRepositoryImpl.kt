package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.CommunityRemoteDataSource
import com.example.teacherforboss.domain.model.community.BossTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentListResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkLikeResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkModifyPostResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkPostsRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkUploadPostRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkUploadPostResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkRequestEntity
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

    override suspend fun uploadBossTalkPost(bossTalkUploadPostRequestEntity: BossTalkUploadPostRequestEntity): BossTalkUploadPostResponseEntity {
        return runCatching {
            communityDataSource.uploadBossTalkPost(bossTalkUploadPostRequestEntity.toBossUploadRequestDto())
                .result.toBossUploadPostResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun getBossTalkBookmark(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkBookmarkResponseEntity {
        return runCatching {
            communityDataSource.getBossTalkBookmark(requestBossTalkDto=bossTalkRequestEntity.toRequestBossTalkDto())
                .result.toBossTalkBookmarkResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun getBossTalkLike(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkLikeResponseEntity {
        return runCatching {
            communityDataSource.getBossTalkLike(requestBossTalkDto=bossTalkRequestEntity.toRequestBossTalkDto())
                .result.toBossTalkLikeResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun getBossTalkBody(bossTalkRequestEntity: BossTalkRequestEntity):BossTalkBodyResponseEntity {
        return runCatching {
            communityDataSource.getBossTalkBody(requestBossTalkDto=bossTalkRequestEntity.toRequestBossTalkDto())
                .result.toBossTalkBodyResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun modifyBossTalkBody(
        bossTalkRequestEntity: BossTalkRequestEntity, bossTalkUploadPostRequestEntity: BossTalkUploadPostRequestEntity
    ): BossTalkModifyPostResponseEntity {
        return runCatching { communityDataSource.modifyBossTalkBody(requestBossTalkDto = bossTalkRequestEntity.toRequestBossTalkDto(),
            requestBossUploadPostDto = bossTalkUploadPostRequestEntity.toBossUploadRequestDto()).result.toBossModifyPostResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun postBossTalkComment(
        bossTalkCommentRequestEntity: BossTalkCommentRequestEntity,
    ): BossTalkUploadPostResponseEntity {
        return runCatching {
            communityDataSource.modifyBossTalkBody(requestBossTalkDto = bossTalkRequestEntity.toRequestBossTalkDto(),
                requestBossUploadPostDto = bossTalkUploadPostRequestEntity.toBossUploadRequestDto()).result.toBossUploadPostResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun getBossTalkCommentList(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkCommentListResponseEntity {
        return runCatching {
            communityDataSource.getBossTalkCommentList(requestBossTalkDto=bossTalkRequestEntity.toRequestBossTalkDto())
                .result.toBossTalkCommentListResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun postBossTalkComment(bossTalkCommentRequestEntity: BossTalkCommentRequestEntity,
        bossTalkRequestEntity: BossTalkRequestEntity
    ): BossTalkCommentResponseEntity {
        return runCatching {
            communityDataSource.postBossTalkComment(requestBossTalkCommentDto=bossTalkCommentRequestEntity.toRequestBossTalkCommentDto(), requestBossTalkDto=bossTalkRequestEntity.toRequestBossTalkDto())
                .result.toBossTalkCommentResponseEntity()
        }.getOrElse { err->throw err }
    }

}