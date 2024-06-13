package com.example.teacherforboss.domain.model.signup

data class SignupEntity(
    val email:String,
    val isChecked:String,
    val password:String,
    val rePassword:String,
    val name:String,
    val gender:String,
    val birthDate:String,
    val phone:String,
    val emailAuthId:Long,
    val phoneAuthId:Long,
    val agreementUsage:String,
    val agreementInfo:String,
    val agreementAge:String,
    val agreementSms:String,
    val agreementEmail:String,
    val agreementLocation:String

)
