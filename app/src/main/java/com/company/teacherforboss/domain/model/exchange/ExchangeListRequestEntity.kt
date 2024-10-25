package com.company.teacherforboss.domain.model.exchange

import com.company.teacherforboss.data.model.request.exchange.RequestExchangeListDto

data class ExchangeListRequestEntity (
    val lastExchangeId: Long,
    val size: Int
) {
    fun toRequestExchangeListDto() = RequestExchangeListDto(
        lastExchangeId = lastExchangeId,
        size = size
    )
}