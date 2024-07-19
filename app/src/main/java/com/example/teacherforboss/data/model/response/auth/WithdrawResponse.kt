package com.example.teacherforboss.data.model.response.auth

import com.example.teacherforboss.domain.model.auth.WithdrawResponseEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class WithdrawResponse(
    @SerializedName("email")
    val email:String,
    @SerializedName("withdrawnAt")
    val withdrawnAt:String,
    ){
    fun toWithdrawEntity()=WithdrawResponseEntity(
        email=email,
        withdrawnAt=withdrawnAt
    )
}
