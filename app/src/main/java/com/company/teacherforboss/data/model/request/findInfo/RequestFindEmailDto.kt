package com.company.teacherforboss.data.model.request.findInfo

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestFindEmailDto(
    @SerializedName("phoneAuthId")
    val phoneAuthId:Long,
)
