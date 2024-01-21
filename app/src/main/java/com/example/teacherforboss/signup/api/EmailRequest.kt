package com.example.teacherforboss.signup.api

import com.google.gson.annotations.SerializedName

data class EmailRequest (
    @SerializedName("email")
    var email:String,
    @SerializedName("purpose")
    var purpose:Int
)