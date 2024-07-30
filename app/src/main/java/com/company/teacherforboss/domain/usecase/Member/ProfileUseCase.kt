package com.company.teacherforboss.domain.usecase.Member

import com.company.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.company.teacherforboss.domain.repository.MemberRepository

class ProfileUseCase (private val memberRepository: MemberRepository) {
    suspend operator fun invoke() : Result<MyPageProfileEntity> = memberRepository.getProfile()
}