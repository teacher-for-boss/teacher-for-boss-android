package com.example.teacherforboss.data.model.response.auth

import com.example.teacherforboss.domain.model.auth.WithdrawResponseEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class WithdrawResponse(
    @SerializedName("memberId")
    val memberId:Long,
    @SerializedName("inactiveDate")
    val inactiveDate:String,
    ){
    fun toWithdrawEntity()=WithdrawResponseEntity(
        memberId=memberId,
        inactiveDate=inactiveDate
    )
}
