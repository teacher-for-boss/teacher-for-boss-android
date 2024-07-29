package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.response.payment.RequestBankAccountChangeDto
import com.company.teacherforboss.data.model.response.payment.ResponseBankAccountChangeDto
import com.company.teacherforboss.data.model.response.payment.ResponseBankAccountDto
import com.company.teacherforboss.util.base.BaseResponse

interface PaymentRemoteDataSource {
    suspend fun getBankAccount() : BaseResponse<ResponseBankAccountDto>
    suspend fun changeBankAccount(requestBankAccountChangeDto: RequestBankAccountChangeDto): BaseResponse<ResponseBankAccountChangeDto>

}