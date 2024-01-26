package com.example.teacherforboss.login

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

data class LoginRequest(
    @SerializedName("email")
    var email:String,
    @SerializedName("password")
    var password:String

)

