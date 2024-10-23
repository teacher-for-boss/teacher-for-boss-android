package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.exchange.ExchangeListRequestEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeListResponseEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeRequestEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeResponseEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.company.teacherforboss.domain.model.payment.BankAccountResponseEntity
import com.company.teacherforboss.domain.model.payment.TeacherPointResponseEntity

interface PaymentRepository {
    suspend fun getBankAccount(): Result<BankAccountResponseEntity>

    suspend fun changeBankAccount(bankAccountChangeRequestEntity: BankAccountChangeRequestEntity): BankAccountChangeResponseEntity

    suspend fun exchange(exchangeRequestEntity: ExchangeRequestEntity): Result<ExchangeResponseEntity>

    suspend fun getTeacherPoint(): Result<TeacherPointResponseEntity>

    suspend fun getExchangeList(exchangeListRequestEntity: ExchangeListRequestEntity): Result<ExchangeListResponseEntity>
}