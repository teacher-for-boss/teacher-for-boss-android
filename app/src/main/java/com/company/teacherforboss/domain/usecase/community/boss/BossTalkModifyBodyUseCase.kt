package com.company.teacherforboss.domain.usecase.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkModifyPostResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkUploadPostRequestEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class BossTalkModifyBodyUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity, bossTalkUploadPostRequestEntity: BossTalkUploadPostRequestEntity): BossTalkModifyPostResponseEntity
    =communityRepository.modifyBossTalkBody(bossTalkRequestEntity,bossTalkUploadPostRequestEntity)
}