package com.company.teacherforboss.data.model.request.signup

import com.google.gson.annotations.SerializedName

data class PhoneRequest(
    @SerializedName("phone")
    var phone:String,
    @SerializedName("purpose")
    var purpose:Int,
    @SerializedName("appHash")
    var appHash:String
)