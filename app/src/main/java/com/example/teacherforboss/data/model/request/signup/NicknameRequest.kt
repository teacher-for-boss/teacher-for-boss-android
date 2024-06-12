package com.example.teacherforboss.data.model.request.signup

import com.google.gson.annotations.SerializedName

data class NicknameRequest(
    @SerializedName("nickname")
    var nickname:String
)