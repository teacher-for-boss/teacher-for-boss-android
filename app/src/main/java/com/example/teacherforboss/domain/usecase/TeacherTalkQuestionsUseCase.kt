package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.TeacherTalkQuestionsRequestEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkQuestionsResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkQuestionsUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(teacherTalkQuestionsRequestEntity: TeacherTalkQuestionsRequestEntity): TeacherTalkQuestionsResponseEntity =
        communityRepository.getTeacherTalkQuestions(teacherTalkQuestionsRequestEntity)
}