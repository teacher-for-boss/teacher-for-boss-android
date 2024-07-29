package com.company.teacherforboss.data.model.response.auth

import com.company.teacherforboss.domain.model.auth.LogoutResponseEntity
import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("email")
    val email:String,
    @SerializedName("accessToken")
    val accessToken:String,
    @SerializedName("logoutAt")
    val logoutAt:String
){
    fun toLogoutResponseEntity()=LogoutResponseEntity(
        email=email,
        accessToken=accessToken,
        logoutAt=logoutAt
    )
}
