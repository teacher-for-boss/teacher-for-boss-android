package com.example.teacherforboss.util.base

import kotlinx.serialization.SerialName

data class ErrorResponseBody(
    @SerialName("isSuccess")
    val isSuccess: Boolean,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
)
