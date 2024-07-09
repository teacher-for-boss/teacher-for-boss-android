package com.example.teacherforboss.domain.usecase.community.boss

import com.example.teacherforboss.domain.model.community.boss.BossTalkCommentLikeRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkCommentLikeResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkCommentLikeUseCase(
    private val communityRepository: CommunityRepository
){
    suspend operator fun invoke(bossTalkCommentLikeRequestEntity: BossTalkCommentLikeRequestEntity): BossTalkCommentLikeResponseEntity =
        communityRepository.postBossTalkCommentLike(bossTalkCommentLikeRequestEntity)
}