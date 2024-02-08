package com.example.teacherforboss.data.model.request.signup

import androidx.lifecycle.LiveData
import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("email")
    var email:String,
    @SerializedName("password")
    var password:String,
    @SerializedName("rePassword")
    var rePassword:String,
    @SerializedName("name")
    var name:String,
    //1남자, 2여자인데 int?
    @SerializedName("gender")
    var gender:Int,
    @SerializedName("birthDate")
    var birthDate:String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("emailAuthId")
    var emailAuthId:Long,
    @SerializedName("phoneAuthId")
    var phoneAuthId:Long,

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