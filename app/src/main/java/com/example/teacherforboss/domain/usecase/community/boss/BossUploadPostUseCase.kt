package com.example.teacherforboss.domain.usecase.community.boss

import com.example.teacherforboss.domain.model.community.boss.BossTalkUploadPostRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkUploadPostResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossUploadPostUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkUploadPostRequestEntity: BossTalkUploadPostRequestEntity): BossTalkUploadPostResponseEntity =
        communityRepository.uploadBossTalkPost(bossTalkUploadPostRequestEntity)
}