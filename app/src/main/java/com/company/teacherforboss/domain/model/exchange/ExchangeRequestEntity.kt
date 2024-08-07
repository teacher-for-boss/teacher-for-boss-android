package com.company.teacherforboss.domain.model.exchange

import com.company.teacherforboss.data.model.request.payment.RequestExchangeDto

data class ExchangeRequestEntity (
    val points: Int
){
    fun toRequestExchangeDto() = RequestExchangeDto(
        points= points
    )
}
