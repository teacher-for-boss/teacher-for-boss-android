package com.company.teacherforboss.domain.usecase.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsResponseEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkQuestionsUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(teacherTalkQuestionsRequestEntity: TeacherTalkQuestionsRequestEntity): TeacherTalkQuestionsResponseEntity =
        communityRepository.getTeacherTalkQuestions(teacherTalkQuestionsRequestEntity)
}