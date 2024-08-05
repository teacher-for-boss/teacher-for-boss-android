package com.company.teacherforboss.domain.usecase.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerListResponseEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkAnswerListUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkAnswerListResponseEntity
    =communityRepository.getTeacherTalkAnswerList(teacherTalkRequestEntity = teacherTalkRequestEntity)
}