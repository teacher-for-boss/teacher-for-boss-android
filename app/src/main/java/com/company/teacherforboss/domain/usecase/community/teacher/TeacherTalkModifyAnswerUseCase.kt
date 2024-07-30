package com.company.teacherforboss.domain.usecase.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherAnswerModifyResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherAnswerPostRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

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