package com.company.teacherforboss.data.model.response.mypage

import com.google.gson.annotations.SerializedName

data class RequestMyPageAnsweredQuestionDto (
    @SerializedName("lastQuestionId")
    val lastQuestionId:Long=0L,
    @SerializedName("size")
    val size:Int=10
)

