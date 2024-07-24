package com.example.teacherforboss.domain.usecase.Member

import com.example.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.example.teacherforboss.domain.repository.MemberRepository
import javax.inject.Inject

class ProfileUseCase (private val memberRepository: MemberRepository) {
    suspend operator fun invoke() = memberRepository.getProfile()
}