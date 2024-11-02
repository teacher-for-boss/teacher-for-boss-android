package com.company.teacherforboss.data.model.request.notification

import com.google.gson.annotations.SerializedName

data class NotificationRequestDto(
    @SerializedName("notificationId")
    val notificationId:Long
)