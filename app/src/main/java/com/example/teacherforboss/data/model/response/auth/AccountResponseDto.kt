package com.example.teacherforboss.data.model.response.auth

import com.example.teacherforboss.domain.model.auth.AccountEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AccountResponseDto(
    @SerializedName("memberId") val memberId:Long,
    @SerializedName("email") val email:String,
    @SerializedName("phone") val phone:String?,
    @SerializedName("loginType") val loginType:String,
){
    fun toAccountEntity()=AccountEntity(
        memberId,email, phone, loginType
    )
}
