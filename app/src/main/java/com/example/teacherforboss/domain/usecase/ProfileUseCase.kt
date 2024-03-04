package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.data.repository.MembersRepositoryImpl
import com.example.teacherforboss.domain.model.ProfileEntity
import com.example.teacherforboss.domain.repository.MembersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val membersRepository: MembersRepository
) {
    suspend operator fun invoke(): ProfileEntity =
        membersRepository.getProfileResult()
}