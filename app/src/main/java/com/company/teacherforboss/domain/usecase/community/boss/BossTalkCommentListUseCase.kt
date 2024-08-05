package com.company.teacherforboss.domain.usecase.community.boss

import com.company.teacherforboss.domain.model.community.BossTalkCommentListResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class BossTalkCommentListUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkCommentListResponseEntity =
        communityRepository.getBossTalkCommentList(bossTalkRequestEntity)
}