package com.example.teacherforboss.presentation.ui.auth.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code")
    override var code:String,

    @SerializedName("isSuccess")
    override var isSuccess:Boolean,

    @SerializedName("message")
    override var message:String,

    @SerializedName("result")
    override var result: loginInterface.Result
):loginInterface
