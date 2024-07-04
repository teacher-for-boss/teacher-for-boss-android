package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkModifyResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherUploadPostRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkModifyBodyUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity,
                                teacherUploadPostRequestEntity: TeacherUploadPostRequestEntity): TeacherTalkModifyResponseEntity
    = communityRepository.modifyTeacherTalkBody(teacherTalkRequestEntity = teacherTalkRequestEntity,
        teacherUploadPostRequestEntity = teacherUploadPostRequestEntity)
}