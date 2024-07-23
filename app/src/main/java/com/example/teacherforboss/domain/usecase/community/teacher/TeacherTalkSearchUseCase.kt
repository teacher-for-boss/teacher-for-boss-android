package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkSearchUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherTalkQuestionsRequestEntity: TeacherTalkQuestionsRequestEntity)
    =communityRepository.searchKeywordTeacherTalk(
        teacherTalkQuestionsRequestEntity = teacherTalkQuestionsRequestEntity
    )
}