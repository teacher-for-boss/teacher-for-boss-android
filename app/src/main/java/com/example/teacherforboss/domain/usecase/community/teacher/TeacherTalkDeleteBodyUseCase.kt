package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkDeleteResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkDeleteBodyUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherTakRequestEntity: TeacherTalkRequestEntity): TeacherTalkDeleteResponseEntity
    = communityRepository.deleteTeacherTalkBody(teacherTalkRequestEntity = teacherTakRequestEntity)
}