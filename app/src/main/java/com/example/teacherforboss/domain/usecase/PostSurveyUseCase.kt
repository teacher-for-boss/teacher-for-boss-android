package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.SurveyEntity
import com.example.teacherforboss.domain.model.SurveyResultEntity
import com.example.teacherforboss.domain.repository.MembersRepository
import kotlinx.coroutines.flow.Flow

class PostSurveyUseCase (
    private val membersRepository: MembersRepository
){
    suspend operator fun invoke(surveyEntity: SurveyEntity): Flow<SurveyResultEntity> =
        membersRepository.postSurveyResult(
            surveyEntity = surveyEntity
        )
}