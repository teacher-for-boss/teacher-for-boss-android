package com.example.teacherforboss.data.model.response.login

import com.google.gson.annotations.SerializedName

data class socialLoginResponse(
    @SerializedName("code")
    override var code:String,

    @SerializedName("isSuccess")
    override var isSuccess:Boolean,

    @SerializedName("message")
    override var message:String,

    @SerializedName("result")
    override var result: LoginResponseInterface.Result
): LoginResponseInterface