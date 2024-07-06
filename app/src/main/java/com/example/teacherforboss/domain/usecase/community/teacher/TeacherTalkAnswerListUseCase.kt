package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherAnswerListResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkAnswerListUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherAnswerListResponseEntity
    =communityRepository.getTeacherTalkAnswerList(teacherTalkRequestEntity = teacherTalkRequestEntity)
}