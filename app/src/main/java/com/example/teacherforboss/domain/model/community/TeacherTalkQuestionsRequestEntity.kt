package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkQuestionsDto

data class TeacherTalkQuestionsRequestEntity (
    val lastQuestionId:Long,
    val size:Int,
    val sortBy:String?,
    val category:String?,
){
    fun toRequestTeacherTalkQuestionsDto()= RequestTeacherTalkQuestionsDto(
        lastQuestionId=lastQuestionId,
        size=size,
        sortBy=sortBy,
        category=category,
    )
}