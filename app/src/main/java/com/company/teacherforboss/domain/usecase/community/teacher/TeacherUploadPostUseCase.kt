package com.company.teacherforboss.domain.usecase.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherUploadPostRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherUploadPostResponseEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class TeacherUploadPostUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherUploadPostRequestEntity: TeacherUploadPostRequestEntity): TeacherUploadPostResponseEntity
    =communityRepository.uploadTeacherTalkPost(teacherUploadPostRequestEntity)
}