package com.company.teacherforboss.domain.usecase.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class BossTalkCommentUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkCommentRequestEntity: BossTalkCommentRequestEntity, bossTalkRequestEntity: BossTalkRequestEntity): BossTalkCommentResponseEntity =
        communityRepository.postBossTalkComment(bossTalkCommentRequestEntity, bossTalkRequestEntity)
}