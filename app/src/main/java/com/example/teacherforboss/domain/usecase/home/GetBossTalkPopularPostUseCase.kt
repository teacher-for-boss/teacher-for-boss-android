package com.example.teacherforboss.domain.usecase.home

import com.example.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import com.example.teacherforboss.domain.repository.HomeRepository

class GetBossTalkPopularPostUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<BossTalkPopularPostEntity>> =
        homeRepository.getBossTalkPopularPost()
}