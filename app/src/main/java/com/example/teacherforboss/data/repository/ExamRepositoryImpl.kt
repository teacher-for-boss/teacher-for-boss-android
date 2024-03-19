package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.ExamRemoteDataSource
import com.example.teacherforboss.data.model.response.exam.ResponseCategory
import com.example.teacherforboss.domain.model.ProfileEntity
import com.example.teacherforboss.domain.model.exams.ExamCategoryEntity
import com.example.teacherforboss.domain.model.exams.ExamResultEntity
import com.example.teacherforboss.domain.model.exams.ExamResultResultEntity
import com.example.teacherforboss.domain.repository.ExamRepository
import com.example.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
    private val examRemoteDataSource: ExamRemoteDataSource
) :ExamRepository{
    override suspend fun GetExamResult(examResultEntity: ExamResultEntity): ExamResultResultEntity {
       return runCatching {
           examRemoteDataSource.getExamResult(requestExamResultDto = examResultEntity.toRequestExamResultDto()
           ).result.toExamResultResultEntity()
       }.getOrElse { err->
           throw err
       }
    }

    override suspend fun GetCategory(): ExamCategoryEntity {
        return runCatching {
            examRemoteDataSource.getCategory().result.toExamCategoryEntity()
        }.getOrElse { err->
            throw err
        }
    }
}