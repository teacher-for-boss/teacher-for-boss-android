package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.BossTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkCommentUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkCommentRequestEntity: BossTalkCommentRequestEntity, bossTalkRequestEntity: BossTalkRequestEntity): BossTalkCommentResponseEntity =
        communityRepository.postBossTalkComment(bossTalkCommentRequestEntity, bossTalkRequestEntity)
}