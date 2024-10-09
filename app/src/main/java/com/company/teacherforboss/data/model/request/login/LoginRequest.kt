package com.company.teacherforboss.data.model.request.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("deviceInfo")
    var deviceInfo: DeviceInfo
){
    data class DeviceInfo(
        @SerializedName("fcmToken")
        var fcmToken: String,

        @SerializedName("platform")
        var platform: String
    )

}