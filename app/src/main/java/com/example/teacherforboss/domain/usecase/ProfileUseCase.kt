package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.data.repository.MembersRepositoryImpl
import com.example.teacherforboss.domain.model.ProfileEntity
import com.example.teacherforboss.domain.repository.MembersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//invoke(): 이름없이 간편하게 호출가능한 함수
class ProfileUseCase @Inject constructor(
    private val membersRepository: MembersRepository
//    private val membersRepositoryImpl: MembersRepositoryImpl
) {
    suspend operator fun invoke(): ProfileEntity =
        membersRepository.getProfileResult()
}