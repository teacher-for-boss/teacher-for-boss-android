package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.response.community.MemberDto
interface Member {
    fun toMemberDto(): MemberDto
}
