package com.example.teacherforboss.domain.model.payment

import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkAnswerDto
import com.example.teacherforboss.data.model.response.payment.RequestBankAccountChangeDto

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