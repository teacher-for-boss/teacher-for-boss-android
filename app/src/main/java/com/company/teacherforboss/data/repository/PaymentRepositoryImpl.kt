package com.company.teacherforboss.data.repository

import com.company.teacherforboss.data.datasource.remote.PaymentRemoteDataSource
import com.company.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.company.teacherforboss.domain.model.payment.BankAccountResponseEntity
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

}
