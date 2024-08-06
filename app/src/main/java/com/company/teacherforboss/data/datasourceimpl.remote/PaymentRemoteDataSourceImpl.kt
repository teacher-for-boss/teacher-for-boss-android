package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.PaymentRemoteDataSource
import com.company.teacherforboss.data.model.response.payment.RequestBankAccountChangeDto
import com.company.teacherforboss.data.model.response.payment.ResponseBankAccountChangeDto
import com.company.teacherforboss.data.model.response.payment.ResponseBankAccountDto
import com.company.teacherforboss.data.service.PaymentService
import com.company.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class PaymentRemoteDataSourceImpl @Inject constructor(
    private val paymentService: PaymentService,
) : PaymentRemoteDataSource {

    override suspend fun getBankAccount() : BaseResponse<ResponseBankAccountDto> =
        paymentService.getBankAccount()
    override suspend fun changeBankAccount(requestBankAccountChangeDto: RequestBankAccountChangeDto): BaseResponse<ResponseBankAccountChangeDto> =
        paymentService.changeBankAccount(requestBankAccountChangeDto)
}