package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.response.community.boss.MemberDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto
import kotlinx.datetime.LocalDateTime

data class BossTalkBodyResponseEntity(
    val title:String,
    val content: String,
    val imageUrlList:List<String>,
    val hashtagList: List<String>,
    val memberInfo: MemberEntity,
    val liked: Boolean,
    val bookmarked: Boolean,
    val likeCount: Int,
    val bookmarkCount: Int,
    val createdAt: String
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
        memberInfo =memberInfo.toMemberDto()

    )
}
data class MemberEntity(
    val memberId: Long,
    val name: String,
    val profileImg: String?,
    val level: String?
){
    fun toMemberDto()= MemberDto(
        memberId=memberId,
        name=name,
        profileImg=profileImg,
        level=level
    )
}