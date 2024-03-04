package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.MembersRemoteDataSource
import com.example.teacherforboss.data.datasourceimpl.remote.MembersRemoteDataSourceImpl
import com.example.teacherforboss.data.mapper.toRequestSurveyDto
import com.example.teacherforboss.domain.model.ProfileEntity
import com.example.teacherforboss.domain.model.SurveyEntity
import com.example.teacherforboss.domain.model.SurveyResultEntity
import com.example.teacherforboss.domain.repository.MembersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MembersRepositoryImpl @Inject constructor(
    private val membersRemoteDataSource: MembersRemoteDataSource,
    ) : MembersRepository {
    override suspend fun postSurveyResult(surveyEntity: SurveyEntity): Flow<SurveyResultEntity> = flow {
        val data = runCatching {
            membersRemoteDataSource.postSurveyResult(
                requestSurveyDto = surveyEntity.toRequestSurveyDto(),
            ).result.toSurveyResultEntity()
        }
        emit(data.getOrThrow())
    }

    override suspend fun getProfileResult(): ProfileEntity {
        return runCatching {
            membersRemoteDataSource.getProfile().result.toProfileEntity()
        }.getOrElse { error->
            throw error
        }
    }
}
