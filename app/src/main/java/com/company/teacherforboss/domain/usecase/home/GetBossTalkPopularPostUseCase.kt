package com.company.teacherforboss.domain.usecase.home

import com.company.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import com.company.teacherforboss.domain.repository.HomeRepository

class GetBossTalkPopularPostUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<BossTalkPopularPostEntity>> =
        homeRepository.getBossTalkPopularPost()
}