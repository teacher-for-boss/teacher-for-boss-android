package com.company.teacherforboss.data.model.request.exchange

import com.google.gson.annotations.SerializedName

data class RequestExchangeListDto (
    @SerializedName("lastExchangeId") val lastExchangeId: Long,
    @SerializedName("size") val size: Int
)