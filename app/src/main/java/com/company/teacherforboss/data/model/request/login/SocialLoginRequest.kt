package com.company.teacherforboss.data.model.request.login

import com.company.teacherforboss.data.model.request.login.LoginRequest.DeviceInfo
import com.google.gson.annotations.SerializedName

data class SocialLoginRequest(
    @SerializedName("email")
    var email:String,

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