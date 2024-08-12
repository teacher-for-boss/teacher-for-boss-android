package com.company.teacherforboss.data.model.response.mypage

import com.company.teacherforboss.domain.model.mypage.ChipInfoResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseChipInfoDto(
    @SerializedName("commentCount") val commentCount: Int,
    @SerializedName("bookmarkCount") val bookmarkCount: Int,
    @SerializedName("point") val point: Int?, //보스면 null
    @SerializedName("questionTicketCount") val questionTicketCount: Int?, //티처면 null
) {
    fun toChipInfoResponseEntity() = ChipInfoResponseEntity(
        commentCount = commentCount,
        bookmarkCount = bookmarkCount,
        point = point,
        questionTicketCount = questionTicketCount
    )
}