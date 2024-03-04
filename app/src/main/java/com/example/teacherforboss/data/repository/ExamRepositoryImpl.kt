package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.ExamRemoteDataSource
import com.example.teacherforboss.data.model.request.exam.RequestExamResultDto
import com.example.teacherforboss.data.model.response.exam.ResponseExamResultDto
import com.example.teacherforboss.domain.model.ExamResultEntity
import com.example.teacherforboss.domain.model.ExamResultResultEntity
import com.example.teacherforboss.domain.model.ProfileEntity
import com.example.teacherforboss.domain.repository.ExamRepository
import com.example.teacherforboss.util.base.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
    private val examRemoteDataSource: ExamRemoteDataSource
) :ExamRepository{
    override suspend fun GetExamResult(examResultEntity: ExamResultEntity): ExamResultResultEntity{
       return runCatching {
           examRemoteDataSource.getExamResult(requestExamResultDto = examResultEntity.toRequestExamResultDto()
           ).result.toExamResultResultEntity()
       }.getOrElse { err->
           throw err
       }
    }
}