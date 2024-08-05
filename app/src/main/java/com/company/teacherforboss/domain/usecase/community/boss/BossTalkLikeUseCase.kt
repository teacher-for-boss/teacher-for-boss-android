package com.company.teacherforboss.domain.usecase.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkLikeResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class BossTalkLikeUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkLikeResponseEntity =
        communityRepository.getBossTalkLike(bossTalkRequestEntity)
}