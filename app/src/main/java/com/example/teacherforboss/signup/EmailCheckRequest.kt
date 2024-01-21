package com.example.teacherforboss.signup

data class EmailCheckRequest (
    @SerializedName("emailAuthId")
    var emailAuthId:Int,
    @SerializedName("emailAuthCode")
    var emailAuthCode:String
)