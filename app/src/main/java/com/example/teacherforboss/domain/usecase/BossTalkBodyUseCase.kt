package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.BossTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkBodyUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkBodyResponseEntity =
        communityRepository.getBossTalkBody(bossTalkRequestEntity)
}