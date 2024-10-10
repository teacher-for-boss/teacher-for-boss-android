package com.company.teacherforboss.domain.model.community.boss

import com.company.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto
import com.company.teacherforboss.domain.model.community.Member

data class BossTalkBodyResponseEntity(
    val title:String,
    val content: String,
    val imageUrlList:List<String>,
    val hashtagList: List<String>,
    val memberInfo: Member,
    val liked: Boolean,
    val bookmarked: Boolean,
    val likeCount: Int,
    val bookmarkCount: Int,
    val commentCount: Int,
    val createdAt: String,
    val isMine:Boolean,
)