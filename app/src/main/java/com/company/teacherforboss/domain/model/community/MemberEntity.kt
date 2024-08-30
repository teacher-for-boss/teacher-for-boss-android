package com.company.teacherforboss.domain.model.community

import com.company.teacherforboss.data.model.response.community.MemberDto

data class MemberEntity(
    val memberId: Long,
    val name: String,
    val profileImg: String?,
    val level: String?,
    val role: String
) : Member {
    override fun toMemberDto() = MemberDto(
        memberId = memberId,
        name = name,
        profileImg = profileImg,
        level = level,
        role = role
    )
}
