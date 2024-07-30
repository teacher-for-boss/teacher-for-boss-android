package com.company.teacherforboss.data.model.request.signup

import com.google.gson.annotations.SerializedName

data class SocialSignupTeacherRequest(
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
    var birthDate: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("profileImg")
    var profileImg:String,
    @SerializedName("businessNumber")
    var businessNumber:String,
    @SerializedName("representative")
    var representative:String,
    @SerializedName("openDate")
    var openDate:String,

    @SerializedName("field")
    var field:String,

    @SerializedName("career")
    var career:Int,

    @SerializedName("introduction")
    var introduction:String,

    @SerializedName("keywords")
    var keywords:List<String>,

    @SerializedName("bank")
    var bank:String,

    @SerializedName("accountNumber")
    var accountNumber:String,

    @SerializedName("accountHolder")
    var accountHolder:String,

)