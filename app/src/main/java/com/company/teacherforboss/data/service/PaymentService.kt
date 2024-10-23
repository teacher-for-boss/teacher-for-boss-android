package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.request.payment.RequestExchangeDto
import com.company.teacherforboss.data.model.response.community.boss.ResponseBossTalkLikeDto
import com.company.teacherforboss.data.model.response.exchange.ResponseExchangeListDto
import com.company.teacherforboss.data.model.response.payment.RequestBankAccountChangeDto
import com.company.teacherforboss.data.model.response.payment.ResponseBankAccountChangeDto
import com.company.teacherforboss.data.model.response.payment.ResponseBankAccountDto
import com.company.teacherforboss.data.model.response.payment.ResponseExchangeDto
import com.company.teacherforboss.data.model.response.payment.ResponseTeacherPointDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface PaymentService {
    @GET("payments/accounts")
    suspend fun getBankAccount(
    ): BaseResponse<ResponseBankAccountDto>

    @PATCH("payments/accounts")
    suspend fun changeBankAccount(
        @Body requestBankAccountChangeDto: RequestBankAccountChangeDto
    ): BaseResponse<ResponseBankAccountChangeDto>

    @POST("payments/exchanges")
    suspend fun exchange(
        @Body requestExchangeDto: RequestExchangeDto
    ):BaseResponse<ResponseExchangeDto>

    @GET("payments/points")
    suspend fun getTeacherPoint(
    ): BaseResponse<ResponseTeacherPointDto>

    @GET("payments/payments/exchanges/history?")
    suspend fun getExchangeList(
        @Query("lastExchangeId") lastExchangeId: Long,
        @Query("size") size: Int
    ): BaseResponse<ResponseExchangeListDto>
}
