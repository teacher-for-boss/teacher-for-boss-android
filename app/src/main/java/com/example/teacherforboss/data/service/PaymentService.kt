package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto
import com.example.teacherforboss.data.model.response.payment.RequestBankAccountChangeDto
import com.example.teacherforboss.data.model.response.payment.ResponseBankAccountChangeDto
import com.example.teacherforboss.data.model.response.payment.ResponseBankAccountDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface PaymentService {
    @GET("payment/accounts")
    suspend fun getBankAccount(
    ): BaseResponse<ResponseBankAccountDto>

    @PATCH("payment/accounts")
    suspend fun changeBankAccount(
        @Body requestBankAccountChangeDto: RequestBankAccountChangeDto
    ): BaseResponse<ResponseBankAccountChangeDto>

}