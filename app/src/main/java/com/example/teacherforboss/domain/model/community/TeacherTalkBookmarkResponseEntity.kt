package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBookmarkDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkBookmarkDto
import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime

data class TeacherTalkBookmarkResponseEntity (
    val bookmarked: Boolean,
    val updatedAt: LocalDateTime,
    val questionId: Long,
){ }