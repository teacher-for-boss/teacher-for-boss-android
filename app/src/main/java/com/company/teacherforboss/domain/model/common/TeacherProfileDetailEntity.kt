package com.company.teacherforboss.domain.model.common

data class TeacherProfileDetailEntity(
    val nickname: String,
    val profileImg: String,
    val introduction: String,
    val phone: String?,
    val phoneOpen:Boolean,
    val email: String?,
    val emailOpen:Boolean,
    val field: String,
    val career: Int,
    val keywords: List<String>,
    val level: String,
    val isMine: Boolean
)
