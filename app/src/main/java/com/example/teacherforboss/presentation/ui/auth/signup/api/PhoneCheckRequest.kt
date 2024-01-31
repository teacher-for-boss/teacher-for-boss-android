package com.example.teacherforboss.signup.api

import com.google.gson.annotations.SerializedName

data class PhoneCheckRequest(
    @SerializedName("phoneAuthId")
    var phoneAuthId:Long,
    @SerializedName("phoneAuthCode")
    var phoneAuthCode:String
)
