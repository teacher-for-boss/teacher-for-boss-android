package com.example.teacherforboss.domain.model.home

data class WeeklyBestTeacherEntity(
    val id: Long,
    val profileImg: String,
    val nickName: String,
    val specialty: String,
    val career: String,
    val keyword: List<String>,
)