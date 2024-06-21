package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.response.community.boss.MemberDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto
import kotlinx.datetime.LocalDateTime

data class BossTalkBodyResponseEntity(
    val title:String,
    val content: String,
    val hashtagList: List<String>,
    val memberInfo: ArrayList<MemberEntity>,
    val liked: Boolean,
    val bookmarked: Boolean,
    val likeCount: Int,
    val bookmarkCount: Int,
    val createdAt: LocalDateTime
) {
    fun toResponseBossTalkBodyDto()= ResponseBossTalkBodyDto(
        title =title,
        content =content,
        hashtagList =hashtagList,
        liked =liked,
        bookmarked =bookmarked,
        likeCount =likeCount,
        bookmarkCount =bookmarkCount,
        createdAt =createdAt,
        memberInfo =memberInfo.mapTo(ArrayList()) { it.toMemberDto() }

    )
}
data class MemberEntity(
    val memberId: Long,
    val name: String,
    val profileImg: String
){
    fun toMemberDto()= MemberDto(
        memberId=memberId,
        name=name,
        profileImg=profileImg
    )
}