package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkQuestionsDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkQuestionsDto
import com.example.teacherforboss.data.model.response.payment.RequestBankAccountChangeDto
import com.example.teacherforboss.data.model.response.payment.ResponseBankAccountChangeDto
import com.example.teacherforboss.data.model.response.payment.ResponseBankAccountDto
import com.example.teacherforboss.util.base.BaseResponse

interface PaymentRemoteDataSource {
    suspend fun getBankAccount() : BaseResponse<ResponseBankAccountDto>
    suspend fun changeBankAccount(requestBankAccountChangeDto: RequestBankAccountChangeDto): BaseResponse<ResponseBankAccountChangeDto>

}