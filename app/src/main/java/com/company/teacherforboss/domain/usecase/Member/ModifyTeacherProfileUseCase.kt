package com.company.teacherforboss.domain.usecase.Member

import com.company.teacherforboss.domain.model.mypage.ModifyProfileResponseEntity
import com.company.teacherforboss.domain.model.mypage.ModifyTeacherProfileRequestEntity
import com.company.teacherforboss.domain.repository.MemberRepository

class ModifyTeacherProfileUseCase(private val memberRepository: MemberRepository) {
    suspend operator fun invoke(modifyTeacherProfileRequestEntity: ModifyTeacherProfileRequestEntity): ModifyProfileResponseEntity
    = memberRepository.modifyTeacherProfile(modifyTeacherProfileRequestEntity)
}