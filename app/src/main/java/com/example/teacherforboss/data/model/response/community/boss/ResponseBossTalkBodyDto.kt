package com.example.teacherforboss.data.model.response.community.boss

import com.example.teacherforboss.domain.model.community.BossTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.MemberEntity
import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime

data class ResponseBossTalkBodyDto(
    @SerializedName("title")
    val title:String,
    @SerializedName("content")
    val content: String,
    @SerializedName("imageUrlList")
    val imageUrlList:List<String>,
    @SerializedName("hashtagList")
    val hashtagList: List<String>,
    @SerializedName("memberInfo")
    val memberInfo: MemberDto,
    @SerializedName("liked")
    val liked: Boolean,
    @SerializedName("bookmarked")
    val bookmarked: Boolean,
    @SerializedName("likeCount")
    val likeCount: Int,
    @SerializedName("bookmarkCount")
    val bookmarkCount: Int,
    @SerializedName("createdAt")
    val createdAt: String
){
    fun toBossTalkBodyResponseEntity(): BossTalkBodyResponseEntity {
        val memberEntities = memberInfo.toMemberEntity()
        return BossTalkBodyResponseEntity(
            title=title,
            content=content,
            imageUrlList=imageUrlList,
            hashtagList=hashtagList,
            liked=liked,
            bookmarked=bookmarked,
            likeCount=likeCount,
            bookmarkCount=bookmarkCount,
            createdAt=createdAt,
            memberInfo=memberEntities
        )
    }

}
data class MemberDto(
    @SerializedName("memberId")
    val memberId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImg")
    val profileImg: String?,
    @SerializedName("level")
    val level: String?
){
    fun toMemberEntity()= MemberEntity(
        memberId=memberId,
        name=name,
        profileImg=profileImg,
        level=level
    )
}
