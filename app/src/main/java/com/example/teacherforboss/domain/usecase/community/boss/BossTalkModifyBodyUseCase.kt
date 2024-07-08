package com.example.teacherforboss.domain.usecase.community.boss

import com.example.teacherforboss.domain.model.community.boss.BossTalkModifyPostResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkUploadPostRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkModifyBodyUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity, bossTalkUploadPostRequestEntity: BossTalkUploadPostRequestEntity): BossTalkModifyPostResponseEntity
    =communityRepository.modifyBossTalkBody(bossTalkRequestEntity,bossTalkUploadPostRequestEntity)
}