package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.exchange.ExchangeRequestEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeResponseEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.company.teacherforboss.domain.model.payment.BankAccountResponseEntity

interface PaymentRepository {
    suspend fun getBankAccount(): Result<BankAccountResponseEntity>
    suspend fun changeBankAccount(bankAccountChangeRequestEntity: BankAccountChangeRequestEntity): BankAccountChangeResponseEntity
    suspend fun exchange(exchangeRequestEntity: ExchangeRequestEntity):ExchangeResponseEntity

}