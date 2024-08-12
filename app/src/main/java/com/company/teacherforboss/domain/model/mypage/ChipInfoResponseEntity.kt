package com.company.teacherforboss.domain.model.mypage

data class ChipInfoResponseEntity (
    val commentCount: Int,
    val bookmarkCount: Int,
    val point: Int?,
    val questionTicketCount: Int?
)