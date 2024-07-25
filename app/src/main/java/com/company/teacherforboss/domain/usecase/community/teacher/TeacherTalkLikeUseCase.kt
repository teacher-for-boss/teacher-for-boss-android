package com.company.teacherforboss.domain.usecase.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkLikeResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkLikeUseCase (
    private val communityRepository: CommunityRepository
){
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkLikeResponseEntity =
        communityRepository.getTeacherTalkLike(teacherTalkRequestEntity)

}