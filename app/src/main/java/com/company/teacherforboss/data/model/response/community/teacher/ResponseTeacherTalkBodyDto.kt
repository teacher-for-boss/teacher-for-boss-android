package com.company.teacherforboss.data.model.response.community.teacher

import com.company.teacherforboss.data.model.request.community.teacher.ExtraContent
import com.company.teacherforboss.data.model.response.community.MemberDto
import com.company.teacherforboss.domain.model.community.MemberEntity
import com.company.teacherforboss.domain.model.community.teacher.ExtraData
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkBodyResponseEntity
import com.google.gson.annotations.SerializedName


data class ResponseTeacherTalkBodyDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("extraData")
    val extraData: ExtraData?,
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
    @SerializedName("answerCount")
    val answerCount: Int,
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
            extraData = extraData,
            category=category,
            imageUrlList = imageUrlList,
            hashtagList=hashtagList,
            liked=liked,
            bookmarked=bookmarked,
            likeCount=likeCount,
            bookmarkCount=bookmarkCount,
            answerCount = answerCount,
            createdAt=createdAt,
            memberInfo=memberEntities,
            isMine=isMine
        )
    }
}