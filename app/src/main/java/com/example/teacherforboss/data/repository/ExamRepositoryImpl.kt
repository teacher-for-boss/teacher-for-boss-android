package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.ExamRemoteDataSource
import com.example.teacherforboss.domain.model.exams.ExamResultEntity
import com.example.teacherforboss.domain.model.exams.ExamResultResultEntity
import com.example.teacherforboss.domain.model.exams.ExamResultWrongNotesEntity
import com.example.teacherforboss.domain.repository.ExamRepository
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

    override suspend fun GetExamResultWrongNotes(examResultEntity: ExamResultEntity): ExamResultWrongNotesEntity {
        return runCatching {
            examRemoteDataSource.getExamResultWrongNotes(requestExamResultDto = examResultEntity.toRequestExamResultDto()
            ).result.toExamResultWrongNotesEntity()
        }.getOrElse { err->
            throw err
        }
    }

}