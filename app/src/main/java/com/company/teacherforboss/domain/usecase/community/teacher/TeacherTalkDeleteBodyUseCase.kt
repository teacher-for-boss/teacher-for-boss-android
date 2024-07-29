package com.company.teacherforboss.domain.usecase.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkDeleteResponseEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkDeleteBodyUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherTakRequestEntity: TeacherTalkRequestEntity): TeacherTalkDeleteResponseEntity
    = communityRepository.deleteTeacherTalkBody(teacherTalkRequestEntity = teacherTakRequestEntity)
}