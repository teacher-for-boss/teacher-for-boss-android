package com.company.teacherforboss.presentation.ui.mypage.exchange

data class ExchangeHistoryItem(
    val type : String,
    val points : Int,
    val time : String
)

data class AskPaymentHistoryItem(
    val type : String,
    val questionTicketCount : Int,
    val time : String
)