package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.TeacherTalkAnsRequestEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkAnsResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkAnsUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(teacherTalkAnsRequestEntity: TeacherTalkAnsRequestEntity): TeacherTalkAnsResponseEntity =
        communityRepository.deleteTeacherTalkAns(teacherTalkAnsRequestEntity)
}