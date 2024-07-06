package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkBodyDto

data class TeacherTalkBodyResponseEntity(
    val title:String,
    val content: String,
    val category: String,
    val imageUrlList: List<String>,
    val hashtagList: List<String>?,
    val memberInfo: Member,
    val liked: Boolean,
    val bookmarked: Boolean,
    val likeCount: Int,
    val bookmarkCount: Int,
    val createdAt: String,
    val isMine: Boolean
){
    fun toResponseTeacherTalkBodyDto() = ResponseTeacherTalkBodyDto(
        title =title,
        content =content,
        category =category,
        imageUrlList = imageUrlList,
        hashtagList =hashtagList,
        liked =liked,
        bookmarked =bookmarked,
        likeCount =likeCount,
        bookmarkCount =bookmarkCount,
        createdAt =createdAt,
        memberInfo =memberInfo.toMemberDto(),
        isMine =isMine
    )
}
