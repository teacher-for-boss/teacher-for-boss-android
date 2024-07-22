package com.example.teacherforboss.domain.usecase.home

import com.example.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import com.example.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity
import com.example.teacherforboss.domain.repository.HomeRepository

class GetTeacherTalkPopularPostUseCase (
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<TeacherTalkPopularPostEntity>> =
        homeRepository.getTeacherTalkPopularPost()
}