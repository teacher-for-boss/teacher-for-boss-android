package com.example.teacherforboss.data.model.response.exam

import com.example.teacherforboss.domain.model.exams.ExamResultResultEntity
import com.google.gson.annotations.SerializedName

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
    fun toExamResultResultEntity()= ExamResultResultEntity(
        score=score,
        questionsNum=questionsNum,
        correctAnsNum=correctAnsNum,
        incorrectAnsNum=incorrectAnsNum
    )
}
