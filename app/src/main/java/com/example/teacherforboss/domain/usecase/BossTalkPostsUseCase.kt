package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.BossTalkPostsRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class BossTalkPostsUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(bossTalkPostsRequestEntity: BossTalkPostsRequestEntity):BossTalkPostsResponseEntity=
        communityRepository.getBossTalkPosts(bossTalkPostsRequestEntity)
}