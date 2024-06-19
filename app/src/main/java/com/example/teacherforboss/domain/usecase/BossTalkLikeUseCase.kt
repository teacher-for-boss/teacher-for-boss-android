package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.BossTalkLikeResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkLikeUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkLikeResponseEntity =
        communityRepository.getBossTalkLike(bossTalkRequestEntity)
}