package com.example.teacherforboss.domain.usecase.community.boss

import com.example.teacherforboss.domain.model.community.boss.BossTalkCommentRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkCommentResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkCommentUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkCommentRequestEntity: BossTalkCommentRequestEntity, bossTalkRequestEntity: BossTalkRequestEntity): BossTalkCommentResponseEntity =
        communityRepository.postBossTalkComment(bossTalkCommentRequestEntity, bossTalkRequestEntity)
}