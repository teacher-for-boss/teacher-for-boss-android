package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.response.community.MemberDto

data class MemberEntity(
    val memberId: Long,
    val name: String,
    val profileImg: String?
) : Member {
    override fun toMemberDto() = MemberDto(
        memberId = memberId,
        name = name,
        profileImg = profileImg
    )
}
