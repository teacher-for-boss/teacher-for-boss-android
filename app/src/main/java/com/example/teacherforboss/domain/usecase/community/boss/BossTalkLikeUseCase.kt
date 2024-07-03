package com.example.teacherforboss.domain.usecase.community.boss

import com.example.teacherforboss.domain.model.community.boss.BossTalkLikeResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkLikeUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkLikeResponseEntity =
        communityRepository.getBossTalkLike(bossTalkRequestEntity)
}