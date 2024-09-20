package com.company.teacherforboss.domain.model.community.teacher

import kotlinx.datetime.LocalDateTime

data class TeacherTalkBookmarkResponseEntity (
    val bookmarked: Boolean,
    val updatedAt: String,
    val questionId: Long,
){ }