package com.example.teacherforboss.domain.usecase.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerLikeRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerLikeResponseEntity
import com.example.teacherforboss.domain.repository.CommunityRepository

class TeacherTalkAnswerDislikeUseCase (
    private val communityRepository: CommunityRepository
){
    suspend operator fun invoke(teacherTalkAnswerLikeRequestEntity: TeacherTalkAnswerLikeRequestEntity): TeacherTalkAnswerLikeResponseEntity =
        communityRepository.postTeacherTalkAnswerDislike(teacherTalkAnswerLikeRequestEntity)
}