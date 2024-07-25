package com.company.teacherforboss.data.model.request.signup

import com.google.gson.annotations.SerializedName

data class BusinessNumberCheckRequest(
    @SerializedName("businessNumber")
    var businessNumber:String,

    @SerializedName("openDate")
    var openDate:String,

    @SerializedName("representative")
    var representative:String
)