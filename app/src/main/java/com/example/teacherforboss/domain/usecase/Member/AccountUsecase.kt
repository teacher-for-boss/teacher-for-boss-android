package com.example.teacherforboss.domain.usecase.Member

import com.example.teacherforboss.domain.model.auth.AccountEntity
import com.example.teacherforboss.domain.repository.MemberRepository
import javax.inject.Inject

class AccountUsecase @Inject constructor(
    val memberRepository: MemberRepository
) {
    suspend operator fun invoke():Result<AccountEntity> = memberRepository.getAccount()
}