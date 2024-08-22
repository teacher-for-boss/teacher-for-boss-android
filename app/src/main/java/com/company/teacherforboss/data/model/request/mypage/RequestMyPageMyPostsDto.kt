package com.company.teacherforboss.data.model.request.mypage

import com.google.gson.annotations.SerializedName

data class RequestMyPageMyPostsDto (
    @SerializedName("lastPostId")
    val lastPostId:Long=0L,
    @SerializedName("size")
    val size:Int=10
)

