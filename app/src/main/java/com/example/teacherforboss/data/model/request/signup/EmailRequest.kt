package com.example.teacherforboss.data.model.request.signup

import com.google.gson.annotations.SerializedName

data class EmailRequest (
    @SerializedName("email")
    var email:String,
    @SerializedName("purpose")
    var purpose:Int
)