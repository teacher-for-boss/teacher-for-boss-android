package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.example.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.example.teacherforboss.domain.model.payment.BankAccountResponseEntity

interface PaymentRepository {
    suspend fun getBankAccount(): Result<BankAccountResponseEntity>
    suspend fun changeBankAccount(bankAccountChangeRequestEntity: BankAccountChangeRequestEntity): BankAccountChangeResponseEntity
}