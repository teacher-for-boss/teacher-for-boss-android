package com.company.teacherforboss.domain.usecase.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerLikeRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerLikeResponseEntity
import com.company.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkAnswerLikeUseCase (
    private val communityRepository: CommunityRepository
){
    suspend operator fun invoke(teacherTalkAnswerLikeRequestEntity: TeacherTalkAnswerLikeRequestEntity) :TeacherTalkAnswerLikeResponseEntity =
        communityRepository.postTeacherTalkAnswerLike(teacherTalkAnswerLikeRequestEntity)
}