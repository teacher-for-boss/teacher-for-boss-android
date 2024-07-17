package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import com.example.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity
import com.example.teacherforboss.domain.model.home.WeeklyBestTeacherEntity

interface HomeRepository {
    suspend fun getBossTalkPopularPost(): Result<List<BossTalkPopularPostEntity>>

    suspend fun getTeacherTalkPopularPost(): Result<List<TeacherTalkPopularPostEntity>>

    suspend fun getWeeklyBestTeacher(): Result<List<WeeklyBestTeacherEntity>>
}
