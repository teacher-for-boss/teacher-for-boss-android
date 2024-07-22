package com.example.teacherforboss.domain.model.common

import androidx.databinding.ObservableBoolean

data class TeacherProfileDetailEntity(
    val memberId: Long,
    val nickname: String,
    val profileImgUrl: String,
    val information: String,
    val phoneNum: String?,
    val email: String?,
    val specialty: String,
    val career: Int,
    val keyword: List<String>,
    val level: String,
    val isMine: Boolean
)
