package com.company.teacherforboss.data.model.request.signup

import com.google.gson.annotations.SerializedName

data class SocialSignupBossRequest(
    @SerializedName("role")
    var role:Int,
    @SerializedName("email")
    var email:String,
    @SerializedName("name")
    var name:String,
    @SerializedName("nickname")
    var nickname:String,
    @SerializedName("gender")
    var gender:Int,
    @SerializedName("birthDate")
    var birthDate: String?,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("profileImg")
    var profileImg:String?,
    )