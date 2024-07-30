package com.company.teacherforboss.domain.usecase.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkUploadPostRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkUploadPostResponseEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class BossUploadPostUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkUploadPostRequestEntity: BossTalkUploadPostRequestEntity): BossTalkUploadPostResponseEntity =
        communityRepository.uploadBossTalkPost(bossTalkUploadPostRequestEntity)
}