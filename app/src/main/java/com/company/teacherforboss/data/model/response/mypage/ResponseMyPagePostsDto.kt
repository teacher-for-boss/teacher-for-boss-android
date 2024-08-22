package com.company.teacherforboss.data.model.response.mypage

import com.company.teacherforboss.domain.model.mypage.MyPagePostsResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseMyPagePostsDto (
    @SerializedName("hasNext")
    val hasNext:Boolean,
    @SerializedName("postList")
    val postList: ArrayList<MyPagePostDto>
){
    fun toMyPagePostsResponseEntity(): MyPagePostsResponseEntity {
        val postList = postList.mapTo(ArrayList()) {it.toMyPagePostEntity()}

        return MyPagePostsResponseEntity(
            hasNext = hasNext,
            postList = postList
        )
    }
}