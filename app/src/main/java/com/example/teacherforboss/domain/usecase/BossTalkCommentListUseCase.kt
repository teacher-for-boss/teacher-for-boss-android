package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.BossTalkCommentListResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkCommentListUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkCommentListResponseEntity =
        communityRepository.getBossTalkCommentList(bossTalkRequestEntity)
}