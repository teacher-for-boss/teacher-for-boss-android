package com.company.teacherforboss.data.model.response.community.boss

import com.company.teacherforboss.data.model.response.community.MemberDto
import com.company.teacherforboss.domain.model.community.MemberEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkBodyResponseEntity
import com.google.gson.annotations.SerializedName

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
    @SerializedName("commentCount")
    val commentCount: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("isMine")
    val isMine:Boolean,
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
            commentCount=commentCount,
            createdAt=createdAt,
            memberInfo=memberEntities,
            isMine=isMine,
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
    val level: String?,
    @SerializedName("role")
    val role: String
){
    fun toMemberEntity()= MemberEntity(
        memberId=memberId,
        name=name,
        profileImg=profileImg,
        level=level,
        role=role
    )
}
