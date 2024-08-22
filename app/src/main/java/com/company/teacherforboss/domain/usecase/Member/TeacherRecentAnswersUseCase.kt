package com.company.teacherforboss.domain.usecase.Member

import com.company.teacherforboss.domain.model.common.TeacherDetailProfileRequestEntity
import com.company.teacherforboss.domain.repository.MemberRepository

class TeacherRecentAnswersUseCase(private val memberRepository: MemberRepository) {
    suspend operator fun invoke(teacherDetailProfileRequestEntity: TeacherDetailProfileRequestEntity)
    = memberRepository.getTeacherRecentAnswers(teacherDetailProfileRequestEntity = teacherDetailProfileRequestEntity)
}