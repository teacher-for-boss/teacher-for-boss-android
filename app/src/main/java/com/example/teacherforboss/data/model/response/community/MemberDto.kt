package com.example.teacherforboss.data.model.response.community

import com.example.teacherforboss.domain.model.community.MemberEntity
import com.google.gson.annotations.SerializedName

data class MemberDto(
    @SerializedName("memberId")
    val memberId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImg")
    val profileImg: String?
){
    fun toMemberEntity()= MemberEntity(
        memberId=memberId,
        name=name,
        profileImg=profileImg
    )
}