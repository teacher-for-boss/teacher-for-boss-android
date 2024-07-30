package com.company.teacherforboss.domain.model.payment

import com.company.teacherforboss.data.model.response.payment.RequestBankAccountChangeDto

data class BankAccountChangeRequestEntity (
    val bank: String,
    val accountHolder: String,
    val accountNumber: String
) {
    fun toRequestBankAccountChangeDto() = RequestBankAccountChangeDto(
        bank = bank,
        accountHolder = accountHolder,
        accountNumber = accountNumber
    )
}