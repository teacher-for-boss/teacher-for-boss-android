package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkBookmarkUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkBookmarkResponseEntity =
        communityRepository.getTeacherTalkBookmark(teacherTalkRequestEntity)
}