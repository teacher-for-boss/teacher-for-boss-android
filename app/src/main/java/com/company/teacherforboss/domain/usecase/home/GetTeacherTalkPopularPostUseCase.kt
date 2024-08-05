package com.company.teacherforboss.domain.usecase.home

import com.company.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity
import com.company.teacherforboss.domain.repository.HomeRepository

class GetTeacherTalkPopularPostUseCase (
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<TeacherTalkPopularPostEntity>> =
        homeRepository.getTeacherTalkPopularPost()
}