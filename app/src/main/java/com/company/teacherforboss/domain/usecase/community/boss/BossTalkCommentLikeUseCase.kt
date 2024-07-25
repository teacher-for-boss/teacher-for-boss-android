package com.company.teacherforboss.domain.usecase.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentLikeRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentLikeResponseEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class BossTalkCommentLikeUseCase(
    private val communityRepository: CommunityRepository
){
    suspend operator fun invoke(bossTalkCommentLikeRequestEntity: BossTalkCommentLikeRequestEntity): BossTalkCommentLikeResponseEntity =
        communityRepository.postBossTalkCommentLike(bossTalkCommentLikeRequestEntity)
}