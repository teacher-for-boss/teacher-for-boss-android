package com.company.teacherforboss.domain.usecase.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkPostsRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkPostsResponseEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class BossTalkSearchUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkPostsRequestEntity: BossTalkPostsRequestEntity): BossTalkPostsResponseEntity =
        communityRepository.searchKeywordBossTalk(bossTalkPostsRequestEntity = bossTalkPostsRequestEntity)
}