package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.BossTalkCommentLikeRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentLikeResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkCommentLikeUseCase(
    private val communityRepository: CommunityRepository
){
    suspend operator fun invoke(bossTalkCommentLikeRequestEntity: BossTalkCommentLikeRequestEntity):BossTalkCommentLikeResponseEntity=
        communityRepository.postBossTalkCommentLike(bossTalkCommentLikeRequestEntity)
}