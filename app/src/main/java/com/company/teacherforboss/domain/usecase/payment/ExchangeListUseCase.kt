package com.company.teacherforboss.domain.usecase.payment

import com.company.teacherforboss.domain.model.exchange.ExchangeListRequestEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeListResponseEntity
import com.company.teacherforboss.domain.repository.PaymentRepository

class ExchangeListUseCase(private val paymentRepository: PaymentRepository) {
    suspend operator fun invoke(exchangeListRequestEntity: ExchangeListRequestEntity): Result<ExchangeListResponseEntity> =
        paymentRepository.getExchangeList(exchangeListRequestEntity = exchangeListRequestEntity)
}