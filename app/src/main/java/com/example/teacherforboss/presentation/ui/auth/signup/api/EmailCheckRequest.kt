package com.example.teacherforboss.presentation.ui.auth.signup.api

import com.google.gson.annotations.SerializedName

data class EmailCheckRequest (
    @SerializedName("emailAuthId")
    var emailAuthId:Int,
    @SerializedName("emailAuthCode")
    var emailAuthCode:String
)