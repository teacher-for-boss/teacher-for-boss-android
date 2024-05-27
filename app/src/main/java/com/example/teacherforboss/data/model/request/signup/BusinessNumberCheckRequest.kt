package com.example.teacherforboss.data.model.request.signup

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class BusinessNumberCheckRequest(
    @SerializedName("businessNumber")
    var businessNumber:String,

    @SerializedName("openDate")
    var openDate:String,

    @SerializedName("representative")
    var representative:String
)