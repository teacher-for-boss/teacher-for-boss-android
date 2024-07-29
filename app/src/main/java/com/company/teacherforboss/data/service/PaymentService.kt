package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.response.payment.RequestBankAccountChangeDto
import com.company.teacherforboss.data.model.response.payment.ResponseBankAccountChangeDto
import com.company.teacherforboss.data.model.response.payment.ResponseBankAccountDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface PaymentService {
    @GET("payment/accounts")
    suspend fun getBankAccount(
    ): BaseResponse<ResponseBankAccountDto>

    @PATCH("payment/accounts")
    suspend fun changeBankAccount(
        @Body requestBankAccountChangeDto: RequestBankAccountChangeDto
    ): BaseResponse<ResponseBankAccountChangeDto>

}