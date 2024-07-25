package com.company.teacherforboss.data.model.request.login

import com.google.gson.annotations.SerializedName

data class SocialLoginRequest(
    @SerializedName("email")
    var email:String,
//    @SerializedName("name")
//    var name:String,
//    @SerializedName("phone")
//    var phone:String,
//    @SerializedName("gender")
//    var gender:Int?,
//    @SerializedName("birthDate")
//    var birthDate: String?,
//    @SerializedName("profileImg")
//    var profileImg:String?

)