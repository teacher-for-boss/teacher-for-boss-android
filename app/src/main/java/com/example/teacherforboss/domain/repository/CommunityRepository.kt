package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.community.BossTalkPostsRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkPostsResponseEntity

interface CommunityRepository {
    suspend fun getBossTalkPosts(bossTalkPostsRequestEntity: BossTalkPostsRequestEntity):BossTalkPostsResponseEntity
}