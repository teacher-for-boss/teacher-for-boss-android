package com.example.teacherforboss.domain.model.home

import com.example.teacherforboss.presentation.type.KeywordType

data class WeeklyBestTeacherEntity(
    val profileImg: String,
    val nickName: String,
    val specialty: String,
    val career: String,
    val keyword: List<KeywordType>,
)