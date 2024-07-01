package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.community.TeacherTalkLikeResponseEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.repository.CommunityRepository
import javax.inject.Inject

class TeacherTalkLikeUseCase (
    private val communityRepository: CommunityRepository
){
    suspend operator fun invoke(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkLikeResponseEntity =
        communityRepository.getTeacherTalkLike(teacherTalkRequestEntity)

}