package com.example.teacherforboss.data.model.request.signup

import com.google.gson.annotations.SerializedName

data class EmailCheckRequest(
    @SerializedName("emailAuthId")
    var emailAuthId: Long,
    @SerializedName("emailAuthCode")
    var emailAuthCode:String
)