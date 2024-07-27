package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.HomeRemoteDataSource
import com.example.teacherforboss.data.datasource.remote.PaymentRemoteDataSource
import com.example.teacherforboss.data.model.response.home.ResponseBossTalkPopularPostDto
import com.example.teacherforboss.data.model.response.home.ResponseTeacherTalkPopularPostDto
import com.example.teacherforboss.data.model.response.home.ResponseWeeklyBestTeacherDto
import com.example.teacherforboss.data.model.response.payment.RequestBankAccountChangeDto
import com.example.teacherforboss.data.model.response.payment.ResponseBankAccountChangeDto
import com.example.teacherforboss.data.model.response.payment.ResponseBankAccountDto
import com.example.teacherforboss.data.service.HomeService
import com.example.teacherforboss.data.service.PaymentService
import com.example.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class PaymentRemoteDataSourceImpl @Inject constructor(
    private val paymentService: PaymentService,
) : PaymentRemoteDataSource {

    override suspend fun getBankAccount() : BaseResponse<ResponseBankAccountDto> =
        paymentService.getBankAccount()
    override suspend fun changeBankAccount(requestBankAccountChangeDto: RequestBankAccountChangeDto): BaseResponse<ResponseBankAccountChangeDto> =
        paymentService.changeBankAccount(requestBankAccountChangeDto)
}