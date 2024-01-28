package com.example.teacherforboss.presentation.ui.auth.login.social

import com.example.teacherforboss.presentation.ui.auth.login.loginInterface
import com.google.gson.annotations.SerializedName

data class socialLoginResponse(
    @SerializedName("code")
    override var code:Int,

    @SerializedName("isSuccess")
    override var isSuccess:Boolean,

    @SerializedName("message")
    override var message:String,

    @SerializedName("result")
    override var result: loginInterface.Result
): loginInterface