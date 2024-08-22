package com.company.teacherforboss.domain.usecase.Member

import com.company.teacherforboss.domain.model.common.TeacherDetailProfileRequestEntity
import com.company.teacherforboss.domain.repository.MemberRepository

class TeacherDetailProfileUseCase(private val memberRepository: MemberRepository) {
    suspend operator fun invoke(teacherDetailProfileRequestEntity: TeacherDetailProfileRequestEntity)
    = memberRepository.getTeacherDetailProfile(teacherDetailProfileRequestEntity = teacherDetailProfileRequestEntity)
}