package com.company.teacherforboss.data.model.request.mypage

import com.google.gson.annotations.SerializedName

data class TeacherDetailProfileRequestDto (
    @SerializedName("memberId") val memberId: Long
)