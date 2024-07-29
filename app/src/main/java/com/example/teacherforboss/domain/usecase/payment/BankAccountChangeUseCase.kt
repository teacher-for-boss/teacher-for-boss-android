package com.example.teacherforboss.domain.usecase.payment

import com.example.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.example.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.example.teacherforboss.domain.model.payment.BankAccountResponseEntity
import com.example.teacherforboss.domain.repository.PaymentRepository

class BankAccountChangeUseCase(private val paymentRepository: PaymentRepository) {
    suspend operator fun invoke(bankAccountChangeRequestEntity: BankAccountChangeRequestEntity) : BankAccountChangeResponseEntity
    = paymentRepository.changeBankAccount(bankAccountChangeRequestEntity)
}