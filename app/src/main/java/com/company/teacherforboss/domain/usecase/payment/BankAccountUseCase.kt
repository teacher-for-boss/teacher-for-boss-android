package com.company.teacherforboss.domain.usecase.payment

import com.company.teacherforboss.domain.model.payment.BankAccountResponseEntity
import com.company.teacherforboss.domain.repository.PaymentRepository
import javax.inject.Inject

class BankAccountUseCase @Inject constructor(private val paymentRepository: PaymentRepository) {
    suspend operator fun invoke() : Result<BankAccountResponseEntity> = paymentRepository.getBankAccount()
}
