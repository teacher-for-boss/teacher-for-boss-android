package com.example.teacherforboss.data.model.response.exam

import com.google.gson.annotations.SerializedName

data class ResponseCategoryDetailDto(
    @SerializedName("categoryDetailList")
    val categoryDetailList:List<categoryDetailDto>
){
    data class categoryDetailDto(
        @SerializedName("name")
        val name:String,
        @SerializedName("id")
        val id:Long
    )
}
