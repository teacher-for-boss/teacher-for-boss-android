package com.example.teacherforboss.data.model.response.payment

import com.example.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBankAccountChangeDto(
    @SerializedName("updatedAt") val updatedAt:String
){
    fun toBankAccountResponseEntity()= BankAccountChangeResponseEntity(updatedAt = updatedAt)
}