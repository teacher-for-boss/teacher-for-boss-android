package com.company.teacherforboss.data.model.response.community.boss

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnsResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherTalkAnsDto(
    @SerializedName("answerId") val answerId:Long,
    @SerializedName("deletedAt") val deletedAt:String?
){
    fun toTeacherTalkAnsResponseEntity()= TeacherTalkAnsResponseEntity(
        answerId=answerId,
        deletedAt=deletedAt
    )
}
