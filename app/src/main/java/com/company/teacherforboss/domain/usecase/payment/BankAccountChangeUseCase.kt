package com.company.teacherforboss.domain.usecase.payment

import com.company.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.company.teacherforboss.domain.repository.PaymentRepository

class BankAccountChangeUseCase(private val paymentRepository: PaymentRepository) {
    suspend operator fun invoke(bankAccountChangeRequestEntity: BankAccountChangeRequestEntity) : BankAccountChangeResponseEntity
    = paymentRepository.changeBankAccount(bankAccountChangeRequestEntity)
}