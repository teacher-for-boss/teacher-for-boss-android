package com.company.teacherforboss.data.model.response.payment

import com.company.teacherforboss.domain.model.exchange.ExchangeResponseEntity
import com.company.teacherforboss.domain.model.payment.BankAccountResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseExchangeDto (
    @SerializedName("exchangeId") val exchangeId:Long,
    @SerializedName("createdAt") val createdAt:String, // LocalDateTime인데 일단 string
){
    fun toExchangeResponseEntity()= ExchangeResponseEntity(
        exchangeId = exchangeId,
        createdAt = createdAt
    )
}