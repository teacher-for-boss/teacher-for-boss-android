package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.response.community.boss.MemberDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto

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
    val createdAt: String,
    val isMine:Boolean,
) {
    fun toResponseBossTalkBodyDto()= ResponseBossTalkBodyDto(
        title =title,
        content =content,
        imageUrlList=imageUrlList,
        hashtagList =hashtagList,
        liked =liked,
        bookmarked =bookmarked,
        likeCount =likeCount,
        bookmarkCount =bookmarkCount,
        createdAt =createdAt,
        memberInfo =memberInfo.toMemberDto(),
        isMine = isMine

    )
}