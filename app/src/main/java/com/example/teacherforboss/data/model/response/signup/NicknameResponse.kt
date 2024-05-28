package com.example.teacherforboss.data.model.response.signup

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class NicknameResponse(
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
        @SerializedName("nicknameCheck")
        var nicknameCheck:Boolean?,

        @SerializedName("nickname")
        var nickname:String?


    )
}
