package com.company.teacherforboss.data.model.response.payment

import com.company.teacherforboss.domain.model.payment.TeacherPointResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherPointDto (
    @SerializedName("points") val points:Int
) {
    fun toTeacherPointResponseEntity() = TeacherPointResponseEntity(
        points = points
    )
}