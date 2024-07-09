package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkQuestionsUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(teacherTalkQuestionsRequestEntity: TeacherTalkQuestionsRequestEntity): TeacherTalkQuestionsResponseEntity =
        communityRepository.getTeacherTalkQuestions(teacherTalkQuestionsRequestEntity)
}