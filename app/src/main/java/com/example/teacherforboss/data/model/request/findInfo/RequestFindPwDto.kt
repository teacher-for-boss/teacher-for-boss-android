package com.example.teacherforboss.data.model.request.findInfo

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestFindPwDto (
    @SerializedName("emailAuthId")
    val emailAuthId:Long
)