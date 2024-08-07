package com.company.teacherforboss.data.model.request.payment

import com.google.gson.annotations.SerializedName

data class RequestExchangeDto (
    @SerializedName("points") val points:Int,
)