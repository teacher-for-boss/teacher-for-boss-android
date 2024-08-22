package com.company.teacherforboss.domain.model.mypage

data class ChipInfoResponseEntity (
    val memberRole: String,
    val answerCount: Long,
    val questionCount: Long,
    val bookmarkCount: Long,
    val points: Int,
    val questionTicketCount: Int
)