package com.company.teacherforboss.presentation.ui.community.boss_talk.main.card

import java.time.LocalDateTime

data class BossTalkMainCard (
    val post_id: Long,
    val title: String,
    val content: String,
    val created_at: LocalDateTime,
    val bookmark_count: String,
    val like_count: String,
    val comment_count: String,
    val liked: Boolean,
    val bookmarked: Boolean,
    )