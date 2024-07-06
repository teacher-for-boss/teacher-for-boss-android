package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkBodyUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkBodyResponseEntity =
        communityRepository.getTeacherTalkBody(teacherTalkRequestEntity)
}