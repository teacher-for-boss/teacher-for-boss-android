package com.example.teacherforboss.data.model.response.exam

import com.example.teacherforboss.domain.model.exams.ExamResultWrongNotesEntity
import com.google.gson.annotations.SerializedName

data class ResponseExamResultWrongNotesDto(
    @SerializedName("examIncorrectQuestionList")
    val examIncorrectQuestionList:List<WrongQuestionDto>

){
    data class WrongQuestionDto(
        @SerializedName("questionId")
        val questionId:Long,
        @SerializedName("questionSequence")
        val questionSequence:Int,
        @SerializedName("questionName")
        val questionName:String
    )


    fun toExamResultWrongNotesEntity():ExamResultWrongNotesEntity{
        val wrongQuestionEntities=examIncorrectQuestionList.map{
            ExamResultWrongNotesEntity.WrongQuestionEntity(
                questionId=it.questionId,
                questionSequence = it.questionSequence,
                questionName = it.questionName,
            )
        }
        return ExamResultWrongNotesEntity(examWrongQuestionList=wrongQuestionEntities)
    }
}
