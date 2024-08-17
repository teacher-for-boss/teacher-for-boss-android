package com.company.teacherforboss.domain.usecase.Member

import com.company.teacherforboss.domain.repository.MemberRepository

class TeacherRecentAnswersUseCase(private val memberRepository: MemberRepository) {
    suspend operator fun invoke() = memberRepository.getTeacherRecentAnswers()
}