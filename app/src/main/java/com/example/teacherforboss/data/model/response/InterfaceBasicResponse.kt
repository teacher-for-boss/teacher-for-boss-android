package com.example.teacherforboss.data.model.response

import com.google.gson.annotations.SerializedName

data class InterfaceBasicResponse<T> (
    @SerializedName("code")
    var code: String,
    @SerializedName("isSuccess")
    var isSuccess: Boolean,
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
    val result:T?
)