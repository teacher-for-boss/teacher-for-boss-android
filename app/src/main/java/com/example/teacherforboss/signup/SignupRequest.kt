package com.example.teacherforboss.signup

data class SignupRequest (
    @SerializedName("email")
    var email:String,
    //설명에는 T,F로 쓰여있는데 Boolean?
    @SerializedName("isChecked")
    var isChecked:String,
    @SerializedName("password")
    var password:String,
    @SerializedName("rePassword")
    var rePassword:String,
    @SerializedName("name")
    var name:String,
    //1남자, 2여자인데 int?
    @SerializedName("gender")
    var gender:String,
    @SerializedName("birthDate")
    var birthDate:String,
    @SerializedName("phone")
    var phone:String,
    @SerializedName("emailAuth")
    var emailAuth:Long,
    @SerializedName("phoneAuthId")
    var phoneAuthId:Long
)