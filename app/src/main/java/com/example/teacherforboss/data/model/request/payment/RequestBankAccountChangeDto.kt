package com.example.teacherforboss.data.model.response.payment

import com.google.gson.annotations.SerializedName

data class RequestBankAccountChangeDto(
    @SerializedName("bank") val bank:String,
    @SerializedName("accountNumber") val accountNumber:String,
    @SerializedName("accountHolder") val accountHolder:String
)