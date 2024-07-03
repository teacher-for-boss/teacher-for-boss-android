package com.example.teacherforboss.domain.usecase.community.boss

import com.example.teacherforboss.domain.model.community.boss.BossTalkPostsRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkPostsUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkPostsRequestEntity: BossTalkPostsRequestEntity): BossTalkPostsResponseEntity =
        communityRepository.getBossTalkPosts(bossTalkPostsRequestEntity)
}