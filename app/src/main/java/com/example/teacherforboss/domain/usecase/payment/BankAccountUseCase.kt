package com.example.teacherforboss.domain.usecase.payment

import com.example.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.example.teacherforboss.domain.model.payment.BankAccountResponseEntity
import com.example.teacherforboss.domain.repository.PaymentRepository

class BankAccountUseCase(private val paymentRepository: PaymentRepository) {
    suspend operator fun invoke() : Result<BankAccountResponseEntity> = paymentRepository.getBankAccount()
}