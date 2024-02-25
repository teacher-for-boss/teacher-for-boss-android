package com.example.teacherforboss.data.model.request.login

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import java.util.Date

data class LoginRequest(
    @SerializedName("email")
    var email:String,
    @SerializedName("password")
    var password:String
)
