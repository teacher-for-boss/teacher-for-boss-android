package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.request.mypage.ModifyBossProfileRequestDto

data class ModifyBossProfileRequestEntity (
    val nickname: String,
    val profileImg: String
) {
    fun toModifyBossProfileRequestDto() = ModifyBossProfileRequestDto(
        nickname = nickname,
        profileImg = profileImg
    )
}