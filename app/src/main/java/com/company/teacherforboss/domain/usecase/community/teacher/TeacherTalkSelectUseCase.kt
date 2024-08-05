package com.company.teacherforboss.domain.usecase.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkSelectUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity, teacherTalkAnswerRequestEntity: TeacherTalkAnswerRequestEntity)
    = communityRepository.selectTeacherTalkAnswer(
        teacherTalkRequestEntity = teacherTalkRequestEntity, teacherTalkAnswerRequestEntity = teacherTalkAnswerRequestEntity)
}