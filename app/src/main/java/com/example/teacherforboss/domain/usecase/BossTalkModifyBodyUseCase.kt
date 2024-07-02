package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.BossTalkModifyPostResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkUploadPostRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkUploadPostResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkModifyBodyUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkRequestEntity: BossTalkRequestEntity,bossTalkUploadPostRequestEntity: BossTalkUploadPostRequestEntity):BossTalkModifyPostResponseEntity
    =communityRepository.modifyBossTalkBody(bossTalkRequestEntity,bossTalkUploadPostRequestEntity)
}