package com.company.teacherforboss.data.model.response.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerLikeResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherTalkAnswerLikeDto (
    @SerializedName("answerId")
    val answerId:Long,
    @SerializedName("liked")
    val liked:Boolean,
    @SerializedName("likedCount")
    val likedCount:Int,
    @SerializedName("dislikedCount")
    val dislikedCount:Int,
    @SerializedName("updatedAt")
    val updatedAt:String
) {
    fun toResponseTeacherTalkAnswerLikeResponseEntity()= TeacherTalkAnswerLikeResponseEntity(
        answerId=answerId,
        liked=liked,
        likedCount=likedCount,
        dislikedCount=dislikedCount,
        updatedAt=updatedAt
    )
}
