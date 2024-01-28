package com.example.teacherforboss.presentation.ui.auth.login.social

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.Date

data class socialLoginRequest(
    @SerializedName("socialType")
    var socialType:Int,
    @SerializedName("email")
    var email:String,
    @SerializedName("name")
    var name:String,
    @SerializedName("phone")
    var phone:String,
    @SerializedName("gender")
    var gender:Int?,
    @SerializedName("birthDate")
    var birthDate: LocalDate?,
    @SerializedName("profileImg")
    var profileImg:String?

)