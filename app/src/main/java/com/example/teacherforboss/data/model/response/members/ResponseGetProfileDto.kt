package com.example.teacherforboss.data.model.response.members

import com.example.teacherforboss.domain.model.ProfileEntity
import com.example.teacherforboss.domain.model.SurveyResultEntity
import com.google.gson.annotations.SerializedName
import java.io.File

data class ResponseGetProfileDto (
    @SerializedName("name")
    val name:String,

    @SerializedName("profileImg")
    val profileImg: String,//소셜 로그인 시는 string으로 보냄 확인 필요
){
    fun toProfileEntity()=ProfileEntity(
        name=name,
        profileImg=profileImg
    )

}