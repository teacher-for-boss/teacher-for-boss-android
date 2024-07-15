package com.example.teacherforboss.domain.model.community.teacher

data class TeacherTalkAnswerLikeResponseEntity (
    val answerId:Long,
    val liked:Boolean,
    val likedCount:Int,
    val dislikedCount:Int,
    val updatedAt:String
)
