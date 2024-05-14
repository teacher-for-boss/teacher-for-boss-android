package com.example.teacherforboss.util.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NullableBaseResponse<T>(
    @SerialName("isSuccess")
    val isSuccess: Int,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: T? = null,
)
