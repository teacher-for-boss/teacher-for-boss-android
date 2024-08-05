package com.company.teacherforboss.domain.usecase.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkBookmarkResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class BossTalkBookmarkUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkBookmarkResponseEntity =
        communityRepository.getBossTalkBookmark(bossTalkRequestEntity)
}