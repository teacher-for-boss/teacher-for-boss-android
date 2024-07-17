package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.home.BossTalkPopularPostEntity

interface HomeRepository {
    suspend fun getBossTalkPopularPost(): Result<List<BossTalkPopularPostEntity>>
}