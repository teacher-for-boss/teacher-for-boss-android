package com.company.teacherforboss.data.model.response.signup

import com.google.gson.annotations.SerializedName

data class PhoneResponse(
    @SerializedName("code")
    var code:String,

    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var result: Result
) {
    data class Result (
        @SerializedName("phoneAuthId")
        var phoneAuthId:Long?,

        @SerializedName("createdAt")
        var createdAt: String?,

        @SerializedName("appHash")
        var appHash:String?,

        @SerializedName("phone")
        var phone:String?
    )
}
