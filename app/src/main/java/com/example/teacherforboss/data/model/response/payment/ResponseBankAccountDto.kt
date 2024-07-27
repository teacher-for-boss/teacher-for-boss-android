package com.example.teacherforboss.data.model.response.payment

import com.example.teacherforboss.domain.model.auth.AccountEntity
import com.example.teacherforboss.domain.model.payment.BankAccountResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBankAccountDto(
    @SerializedName("bank") val bank:String,
    @SerializedName("accountNumber") val accountNumber:String,
    @SerializedName("accountHolder") val accountHolder:String
){
    fun toBankAccountResponseEntity()= BankAccountResponseEntity(
        bank = bank,
        accountNumber = accountNumber,
        accountHolder = accountHolder
    )
}