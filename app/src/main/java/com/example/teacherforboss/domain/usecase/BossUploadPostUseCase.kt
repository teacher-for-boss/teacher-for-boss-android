package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.BossTalkUploadPostRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkUploadPostResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossUploadPostUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkUploadPostRequestEntity: BossTalkUploadPostRequestEntity):BossTalkUploadPostResponseEntity=
        communityRepository.uploadBossTalkPost(bossTalkUploadPostRequestEntity)
}