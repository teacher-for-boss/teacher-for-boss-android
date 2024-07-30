package com.company.teacherforboss.data.model.response.community.teacher

import com.company.teacherforboss.data.model.response.community.MemberDto
import com.company.teacherforboss.domain.model.community.MemberEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkBodyResponseEntity
import com.google.gson.annotations.SerializedName


data class ResponseTeacherTalkBodyDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("imageUrlList")
    val imageUrlList: List<String>,
    @SerializedName("hashtagList")
    val hashtagList: List<String>?,
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
    val createdAt: String,
    @SerializedName("isMine")
    val isMine: Boolean
){
    fun toTeacherTalkBodyResponseEntity(): TeacherTalkBodyResponseEntity {
        val memberEntities = memberInfo.toMemberEntity()
        return TeacherTalkBodyResponseEntity(
            title=title,
            content=content,
            category=category,
            imageUrlList = imageUrlList,
            hashtagList=hashtagList,
            liked=liked,
            bookmarked=bookmarked,
            likeCount=likeCount,
            bookmarkCount=bookmarkCount,
            createdAt=createdAt,
            memberInfo=memberEntities,
            isMine=isMine
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
        level = level
    )
}
