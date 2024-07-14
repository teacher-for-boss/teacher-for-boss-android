package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerListResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkAnswerListUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkAnswerListResponseEntity
    =communityRepository.getTeacherTalkAnswerList(teacherTalkRequestEntity = teacherTalkRequestEntity)
}