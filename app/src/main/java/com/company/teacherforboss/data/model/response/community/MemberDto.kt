package com.company.teacherforboss.data.model.response.community

import com.company.teacherforboss.domain.model.community.MemberEntity
import com.google.gson.annotations.SerializedName

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
    val role:String
){
    fun toMemberEntity()= MemberEntity(
        memberId=memberId,
        name=name,
        profileImg=profileImg,
        level = level,
        role = role
    )
}