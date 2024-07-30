package com.company.teacherforboss.domain.usecase.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnsRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnsResponseEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkAnsUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(teacherTalkAnsRequestEntity: TeacherTalkAnsRequestEntity): TeacherTalkAnsResponseEntity =
        communityRepository.deleteTeacherTalkAns(teacherTalkAnsRequestEntity)
}