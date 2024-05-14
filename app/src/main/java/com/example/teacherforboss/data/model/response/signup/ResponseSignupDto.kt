package com.example.teacherforboss.data.model.response.signup

import com.example.teacherforboss.domain.model.SignupResultEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ResponseSignupDto(
    @SerializedName("memberId")
    var memberId:Long,
    @SerializedName("createdAt")
    var createdAt: String,
){
    fun toSignupResultEntity()=SignupResultEntity(
        memberId=memberId,
        createdAt=createdAt
    )
}
