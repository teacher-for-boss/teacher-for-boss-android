package com.company.teacherforboss.data.model.response.exchange

import com.company.teacherforboss.domain.model.exchange.ExchangeListResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseExchangeListDto (
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("exchangeList") val exchangeList: List<ExchangeDto>
) {
    data class ExchangeDto (
        @SerializedName("exchangeId") val exchangeId: Long,
        @SerializedName("type") val type: String,
        @SerializedName("points") val points: Int,
        @SerializedName("time") val time: String
    ) {
        fun toExchangeEntity() = ExchangeListResponseEntity.ExchangeEntity(
            exchangeId = exchangeId,
            type = type,
            points = points,
            time = time
        )
    }

    fun toExchangeListResponseEntity() = ExchangeListResponseEntity(
        hasNext = hasNext,
        exchangeList = exchangeList.map { it.toExchangeEntity() }
    )
}