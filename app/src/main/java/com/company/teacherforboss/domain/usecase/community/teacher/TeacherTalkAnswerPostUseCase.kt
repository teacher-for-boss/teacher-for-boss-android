package com.company.teacherforboss.domain.usecase.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherAnswerPostResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherAnswerPostRequestEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkAnswerPostUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity,
                                teacherAnswerPostRequestEntity: TeacherAnswerPostRequestEntity): TeacherAnswerPostResponseEntity
    = communityRepository.postTeacherTalkAnswer(
        teacherTalkRequestEntity = teacherTalkRequestEntity, teacherAnswerPostRequestEntity = teacherAnswerPostRequestEntity)
}