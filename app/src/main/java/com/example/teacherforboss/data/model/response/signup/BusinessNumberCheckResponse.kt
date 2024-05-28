package com.example.teacherforboss.data.model.response.signup

import com.google.gson.annotations.SerializedName

data class BusinessNumberCheckResponse(
    @SerializedName("checked")
    var checked:Boolean,
)
