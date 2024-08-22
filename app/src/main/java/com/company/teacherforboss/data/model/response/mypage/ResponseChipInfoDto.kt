package com.company.teacherforboss.data.model.response.mypage

import com.company.teacherforboss.domain.model.mypage.ChipInfoResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseChipInfoDto(
    @SerializedName("memberRole") val memberRole: String, // BOSS / TEACHER
    @SerializedName("answerCount") val answerCount: Long,
    @SerializedName("questionCount") val questionCount: Long,
    @SerializedName("bookmarkCount") val bookmarkCount: Long,
    @SerializedName("points") val points: Int, //보스면 0
    @SerializedName("questionTicketCount") val questionTicketCount: Int, //티처면 0
) {
    fun toChipInfoResponseEntity() = ChipInfoResponseEntity(
        memberRole = memberRole,
        answerCount = answerCount,
        questionCount = questionCount,
        bookmarkCount = bookmarkCount,
        points = points,
        questionTicketCount = questionTicketCount
    )
}
