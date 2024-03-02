package com.example.teacherforboss.data.model.response.exam

import com.example.teacherforboss.domain.model.ExamResultEntity
import com.example.teacherforboss.domain.model.ExamResultResultEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class ResponseExamResultDto (
    @SerializedName("score")
    val score:Int,
    @SerializedName("questionsNum")
    val questionsNum:Int,
    @SerializedName("correctAnsNum")
    val correctAnsNum:Int,
    @SerializedName("incorrectAnsNum")
    val incorrectAnsNum:Int
){
    fun toExamResultResultEntity()=ExamResultResultEntity(
        score=score,
        questionsNum=questionsNum,
        correctAnsNum=correctAnsNum,
        incorrectAnsNum=incorrectAnsNum
    )
}
