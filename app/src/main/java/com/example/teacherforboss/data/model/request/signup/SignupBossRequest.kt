package com.example.teacherforboss.data.model.request.signup

import com.google.gson.annotations.SerializedName

data class SignupBossRequest(
    @SerializedName("role")
    var role:Int,
    @SerializedName("email")
    var email:String,
    @SerializedName("password")
    var password:String,
    @SerializedName("rePassword")
    var rePassword:String,
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
    @SerializedName("emailAuthId")
    var emailAuthId:Long,
    @SerializedName("phoneAuthId")
    var phoneAuthId:Long,
    @SerializedName("profileImg")
    var profileImg:String,
    @SerializedName("agreementUsage")
    var agreementUsage:String,

    @SerializedName("agreementInfo")
    var agreementInfo:String,

    @SerializedName("agreementAge")
    var agreementAge:String,

    @SerializedName("agreementSms")
    var agreementSms:String,

    @SerializedName("agreementEmail")
    var agreementEmail:String,

    @SerializedName("agreementLocation")
    var agreementLocation:String,

    )