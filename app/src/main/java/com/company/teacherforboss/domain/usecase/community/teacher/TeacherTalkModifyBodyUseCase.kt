package com.company.teacherforboss.domain.usecase.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkModifyResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherUploadPostRequestEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkModifyBodyUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity,
                                teacherUploadPostRequestEntity: TeacherUploadPostRequestEntity): TeacherTalkModifyResponseEntity
    = communityRepository.modifyTeacherTalkBody(teacherTalkRequestEntity = teacherTalkRequestEntity,
        teacherUploadPostRequestEntity = teacherUploadPostRequestEntity)
}