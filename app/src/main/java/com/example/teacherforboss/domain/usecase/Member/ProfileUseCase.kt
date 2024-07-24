package com.example.teacherforboss.domain.usecase.Member

import com.example.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.example.teacherforboss.domain.repository.MemberRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(val memberRepository: MemberRepository) {
    suspend operator fun invoke(): MyPageProfileEntity = memberRepository.getProfile()
}