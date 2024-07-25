package com.company.teacherforboss.domain.model.community

import com.company.teacherforboss.data.model.response.community.MemberDto
interface Member {
    fun toMemberDto(): MemberDto
}
