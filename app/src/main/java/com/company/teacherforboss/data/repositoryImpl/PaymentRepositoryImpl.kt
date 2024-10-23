package com.company.teacherforboss.data.repositoryImpl

import com.company.teacherforboss.data.datasource.remote.PaymentRemoteDataSource
import com.company.teacherforboss.domain.model.exchange.ExchangeListRequestEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeListResponseEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeRequestEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeResponseEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.company.teacherforboss.domain.model.payment.BankAccountResponseEntity
import com.company.teacherforboss.domain.model.payment.TeacherPointResponseEntity
import com.company.teacherforboss.domain.repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentRemoteDataSource: PaymentRemoteDataSource
) : PaymentRepository {
    override suspend fun getBankAccount(): Result<BankAccountResponseEntity> =
        runCatching {
            paymentRemoteDataSource.getBankAccount().result.toBankAccountResponseEntity()
        }

    override suspend fun changeBankAccount(bankAccountChangeRequestEntity: BankAccountChangeRequestEntity): BankAccountChangeResponseEntity =
        runCatching {
            paymentRemoteDataSource.changeBankAccount(requestBankAccountChangeDto = bankAccountChangeRequestEntity.toRequestBankAccountChangeDto())
                .result.toBankAccountResponseEntity()
        }.getOrElse { err -> throw err }

    override suspend fun exchange(exchangeRequestEntity: ExchangeRequestEntity): Result<ExchangeResponseEntity> =
        runCatching {
            paymentRemoteDataSource.exchange(requestExchangeDto = exchangeRequestEntity.toRequestExchangeDto())
                .result.toExchangeResponseEntity()
        }

    override suspend fun getTeacherPoint() :Result<TeacherPointResponseEntity> =
        runCatching {
            paymentRemoteDataSource.getTeacherPoint().result.toTeacherPointResponseEntity()
        }

    override suspend fun getExchangeList(exchangeListRequestEntity: ExchangeListRequestEntity): Result<ExchangeListResponseEntity> =
        runCatching {
            paymentRemoteDataSource.getExchangeList(requestExchangeListDto = exchangeListRequestEntity.toRequestExchangeListDto())
                .result.toExchangeListResponseEntity()
        }
}
