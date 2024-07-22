package com.example.teacherforboss.domain.usecase.home

import com.example.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity
import com.example.teacherforboss.domain.model.home.WeeklyBestTeacherEntity
import com.example.teacherforboss.domain.repository.HomeRepository

class GetWeeklyBestTeacherUseCase (
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<WeeklyBestTeacherEntity>> =
        homeRepository.getWeeklyBestTeacher()
}