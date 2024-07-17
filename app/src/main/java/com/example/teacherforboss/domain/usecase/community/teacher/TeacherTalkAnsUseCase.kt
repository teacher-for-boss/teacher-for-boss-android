package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkAnsRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkAnsResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkAnsUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(teacherTalkAnsRequestEntity: TeacherTalkAnsRequestEntity): TeacherTalkAnsResponseEntity =
        communityRepository.deleteTeacherTalkAns(teacherTalkAnsRequestEntity)
}