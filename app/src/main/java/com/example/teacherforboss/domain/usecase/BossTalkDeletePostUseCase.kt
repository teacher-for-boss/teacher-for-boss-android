package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.BossTalkDeletePostResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkDeletePostUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkDeletePostResponseEntity =
        communityRepository.deleteBossTalkPost(bossTalkRequestEntity)
}