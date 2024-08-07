package com.company.teacherforboss.data.model.request.mypage

import com.google.gson.annotations.SerializedName

data class ModifyBossProfileRequestDto (
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileImg") val profileImg: String
)