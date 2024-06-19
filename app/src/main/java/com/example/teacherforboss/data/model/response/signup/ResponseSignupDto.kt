package com.example.teacherforboss.data.model.response.signup

import com.example.teacherforboss.domain.model.signup.SignupResultEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignupDto(
    @SerializedName("memberId")
    var memberId:Long,
    @SerializedName("createdAt")
    var createdAt: String,
){
    fun toSignupResultEntity()= SignupResultEntity(
        memberId=memberId,
        createdAt=createdAt
    )
}
