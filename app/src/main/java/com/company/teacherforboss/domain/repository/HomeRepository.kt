package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import com.company.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity
import com.company.teacherforboss.domain.model.home.WeeklyBestTeacherEntity

interface HomeRepository {
    suspend fun getBossTalkPopularPost(): Result<List<BossTalkPopularPostEntity>>

    suspend fun getTeacherTalkPopularPost(): Result<List<TeacherTalkPopularPostEntity>>

    suspend fun getWeeklyBestTeacher(): Result<List<WeeklyBestTeacherEntity>>
}
