package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import com.example.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity

interface HomeRepository {
    suspend fun getBossTalkPopularPost(): Result<List<BossTalkPopularPostEntity>>

    suspend fun getTeacherTalkPopularPost(): Result<List<TeacherTalkPopularPostEntity>>
}