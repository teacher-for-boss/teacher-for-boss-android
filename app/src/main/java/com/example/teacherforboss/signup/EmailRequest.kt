package com.example.teacherforboss.signup

data class EmailRequest (
    @SerializedName("email")
    var email:String,
    @SerializedName("purpose")
    var purpose:Int
)