package com.company.teacherforboss.domain.usecase.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkBookmarkResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkBookmarkUseCase (
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkBookmarkResponseEntity =
        communityRepository.getTeacherTalkBookmark(teacherTalkRequestEntity)
}