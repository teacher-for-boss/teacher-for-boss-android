package com.company.teacherforboss.domain.usecase.mypage

import com.company.teacherforboss.domain.model.mypage.ChipInfoResponseEntity
import com.company.teacherforboss.domain.repository.MyPageRepository
import javax.inject.Inject

class ChipInfoUseCase @Inject constructor(private val myPageRepository: MyPageRepository){
    suspend operator fun invoke() : Result<ChipInfoResponseEntity> =
        myPageRepository.getChipInfo()
}