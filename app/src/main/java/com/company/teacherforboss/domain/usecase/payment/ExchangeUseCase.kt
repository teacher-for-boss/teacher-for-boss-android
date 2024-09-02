package com.company.teacherforboss.domain.usecase.payment

import com.company.teacherforboss.domain.model.exchange.ExchangeRequestEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeResponseEntity
import com.company.teacherforboss.domain.repository.PaymentRepository
import javax.inject.Inject

class ExchangeUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(exchangeRequestEntity: ExchangeRequestEntity): Result<ExchangeResponseEntity> =
        paymentRepository.exchange(exchangeRequestEntity)
}