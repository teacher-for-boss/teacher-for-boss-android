package com.company.teacherforboss.data.model.response.auth

import com.company.teacherforboss.domain.model.auth.AccountEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AccountResponseDto(
    @SerializedName("email") val email:String,
    @SerializedName("phone") val phone:String?,
    @SerializedName("loginType") val loginType:String,
){
    fun toAccountEntity()=AccountEntity(
        email, phone, loginType
    )
}
