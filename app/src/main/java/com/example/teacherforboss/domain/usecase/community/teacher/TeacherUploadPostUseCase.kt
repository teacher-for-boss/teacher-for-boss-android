package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherUploadPostRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherUploadPostResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherUploadPostUseCase(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(teacherUploadPostRequestEntity: TeacherUploadPostRequestEntity): TeacherUploadPostResponseEntity
    =communityRepository.uploadTeacherTalkPost(teacherUploadPostRequestEntity)
}