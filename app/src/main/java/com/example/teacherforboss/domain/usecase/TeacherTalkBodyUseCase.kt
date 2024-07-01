package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.TeacherTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository
import javax.inject.Inject

class TeacherTalkBodyUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkBodyResponseEntity =
        communityRepository.getTeacherTalkBody(teacherTalkRequestEntity)
}