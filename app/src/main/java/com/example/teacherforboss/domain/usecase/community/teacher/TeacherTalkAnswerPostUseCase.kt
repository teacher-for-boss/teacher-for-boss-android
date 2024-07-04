package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.model.community.boss.TeacherAnswerPostResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherAnswerPostRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkAnswerPostUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity,
                                teacherAnswerPostRequestEntity: TeacherAnswerPostRequestEntity): TeacherAnswerPostResponseEntity
    = communityRepository.postTeacherTalkAnswer(
        teacherTalkRequestEntity = teacherTalkRequestEntity, teacherAnswerPostRequestEntity = teacherAnswerPostRequestEntity)
}