package com.example.teacherforboss.domain.usecase.community.boss

import com.example.teacherforboss.domain.model.community.boss.BossTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkBookmarkUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkBookmarkResponseEntity =
        communityRepository.getBossTalkBookmark(bossTalkRequestEntity)
}