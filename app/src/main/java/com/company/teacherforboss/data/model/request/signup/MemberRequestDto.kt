package com.company.teacherforboss.data.model.request.signup

import com.google.gson.annotations.SerializedName

data class MemberRequestDto (
    @SerializedName("Member-Id") val memberId: Long
)