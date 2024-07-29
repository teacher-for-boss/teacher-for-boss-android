package com.company.teacherforboss.domain.usecase.Member

import com.company.teacherforboss.domain.model.auth.AccountEntity
import com.company.teacherforboss.domain.repository.MemberRepository
import javax.inject.Inject

class AccountUsecase @Inject constructor(
    val memberRepository: MemberRepository
) {
    suspend operator fun invoke():Result<AccountEntity> = memberRepository.getAccount()
}