package com.example.teacherforboss.data.model.request.signup

import com.google.gson.annotations.SerializedName

data class PhoneCheckRequest(
    @SerializedName("phoneAuthId")
    var phoneAuthId:Long,
    @SerializedName("phoneAuthCode")
    var phoneAuthCode:String
)
