package com.example.teacherforboss.domain.usecase.community.boss

import com.example.teacherforboss.domain.model.community.boss.BossTalkCommentListResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkCommentListUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkCommentListResponseEntity =
        communityRepository.getBossTalkCommentList(bossTalkRequestEntity)
}