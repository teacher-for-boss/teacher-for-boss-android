package com.company.teacherforboss.domain.model.exchange

data class ExchangeListResponseEntity (
    val hasNext: Boolean,
    val exchangeList: List<ExchangeEntity>
) {
    data class ExchangeEntity (
        val exchangeId: Long,
        val type: String,
        val points: Int,
        val time: String
    )
}