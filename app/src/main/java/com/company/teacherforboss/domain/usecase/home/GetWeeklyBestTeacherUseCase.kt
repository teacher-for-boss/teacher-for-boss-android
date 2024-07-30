package com.company.teacherforboss.domain.usecase.home

import com.company.teacherforboss.domain.model.home.WeeklyBestTeacherEntity
import com.company.teacherforboss.domain.repository.HomeRepository

class GetWeeklyBestTeacherUseCase (
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<WeeklyBestTeacherEntity>> =
        homeRepository.getWeeklyBestTeacher()
}