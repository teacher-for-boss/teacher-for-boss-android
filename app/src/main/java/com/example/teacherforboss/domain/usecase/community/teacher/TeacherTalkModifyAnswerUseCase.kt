package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherAnswerModifyResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherAnswerPostRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkModifyAnswerUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity,
                                teacherTalkAnswerRequestEntity: TeacherTalkAnswerRequestEntity,
                                teacherAnswerPostRequestEntity: TeacherAnswerPostRequestEntity
    ): TeacherAnswerModifyResponseEntity
    = communityRepository.modifyTeacherTalkAnswer(
        teacherTalkRequestEntity = teacherTalkRequestEntity,
        teacherTalkAnswerRequestEntity = teacherTalkAnswerRequestEntity,
        teacherAnswerPostRequestEntity = teacherAnswerPostRequestEntity
    )
}