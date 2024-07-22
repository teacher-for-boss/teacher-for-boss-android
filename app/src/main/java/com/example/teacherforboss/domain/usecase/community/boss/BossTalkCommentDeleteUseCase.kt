package com.example.teacherforboss.domain.usecase.community.boss

import com.example.teacherforboss.domain.model.community.boss.BossTalkCommentLikeRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkCommentDeleteUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(bossTalkCommentLikeRequestEntity: BossTalkCommentLikeRequestEntity)
    = communityRepository.deleteBossTalkComment(
        bossTalkCommentLikeRequestEntity = bossTalkCommentLikeRequestEntity
    )
}