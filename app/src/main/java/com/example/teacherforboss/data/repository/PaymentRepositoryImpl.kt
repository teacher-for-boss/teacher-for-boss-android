package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.PaymentRemoteDataSource
import com.example.teacherforboss.data.model.response.payment.RequestBankAccountChangeDto
import com.example.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import com.example.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.example.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.example.teacherforboss.domain.model.payment.BankAccountResponseEntity
import com.example.teacherforboss.domain.repository.PaymentRepository
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

}
