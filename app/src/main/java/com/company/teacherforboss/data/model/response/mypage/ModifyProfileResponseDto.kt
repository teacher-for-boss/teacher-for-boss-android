package com.company.teacherforboss.data.model.response.mypage

import com.company.teacherforboss.domain.model.mypage.ModifyProfileResponseEntity
import com.google.gson.annotations.SerializedName

data class ModifyProfileResponseDto (
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileImg") val profileImg: String
) {
    fun toModifyProfileResponseEntity() = ModifyProfileResponseEntity(
        nickname = nickname,
        profileImg = profileImg
    )
}