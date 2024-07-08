package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkLikeResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkLikeUseCase (
    private val communityRepository: CommunityRepository
){
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkLikeResponseEntity =
        communityRepository.getTeacherTalkLike(teacherTalkRequestEntity)

}