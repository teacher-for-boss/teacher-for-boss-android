package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.TeacherTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkBookmarkUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkBookmarkResponseEntity =
        communityRepository.getTeacherTalkBookmark(teacherTalkRequestEntity)
}