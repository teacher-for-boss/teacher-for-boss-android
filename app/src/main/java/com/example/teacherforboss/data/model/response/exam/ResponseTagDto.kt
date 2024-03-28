package com.example.teacherforboss.data.model.response.exam

import com.example.teacherforboss.domain.model.exams.ExamTagEntity
import com.google.gson.annotations.SerializedName

data class ResponseTagDto(
    @SerializedName("tagList")
    val tagList:List<TagDto>
){
    data class TagDto(
        @SerializedName("tagId")
        val tagId:Long,
        @SerializedName("tagName")
        val tagName:String
    )

    fun toTagEntity() = ExamTagEntity(tagList=tagList)
}
