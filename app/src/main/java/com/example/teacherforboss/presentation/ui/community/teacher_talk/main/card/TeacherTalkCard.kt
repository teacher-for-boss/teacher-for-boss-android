package com.example.teacherforboss.presentation.ui.community.teacher_talk.main.card

import java.time.LocalDateTime

data class TeacherTalkCard (
    val question_id: Long,
    val title: String,
    val content: String,
    val solved: Boolean,
    val selected_teacher: String, //url or null
    val statement_answer: String,
    val bookmark_count: String,
    val like_count: String,
    val comment_count: String,
    val liked: Boolean,
    val bookmarked: Boolean,
    val created_at: LocalDateTime,

    )
