package com.example.teacherforboss.data.model.request.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsRequestEntity
import com.google.gson.annotations.SerializedName

data class RequestTeacherTalkQuestionsDto (
    @SerializedName("lastQuestionId")
    val lastQuestionId:Long=0L,
    @SerializedName("size")
    val size:Int=10,
    @SerializedName("sortBy")
    val sortBy:String?,
    @SerializedName("category")
    val category:String?,
    @SerializedName("keyword")
    val keyword: String?
)