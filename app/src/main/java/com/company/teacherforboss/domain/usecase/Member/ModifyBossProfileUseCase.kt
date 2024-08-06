package com.company.teacherforboss.domain.usecase.Member

import com.company.teacherforboss.domain.model.mypage.ModifyBossProfileRequestEntity
import com.company.teacherforboss.domain.model.mypage.ModifyProfileResponseEntity
import com.company.teacherforboss.domain.repository.MemberRepository

class ModifyBossProfileUseCase(private val memberRepository: MemberRepository) {
    suspend operator fun invoke(modifyBossProfileRequestEntity: ModifyBossProfileRequestEntity): ModifyProfileResponseEntity
    = memberRepository.modifyBossProfile(modifyBossProfileRequestEntity)
}