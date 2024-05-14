package com.example.teacherforboss.data.model.response.signup

import com.google.gson.annotations.SerializedName

data class PhoneCheckResponse(
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
        @SerializedName("checked")
        var checked:Boolean?,

        @SerializedName("phoneAuthCode")
        var phoneAuthCode:String?
    )
}
