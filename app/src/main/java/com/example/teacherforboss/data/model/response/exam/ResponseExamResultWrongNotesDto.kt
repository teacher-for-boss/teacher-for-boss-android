package com.example.teacherforboss.data.model.response.exam

import com.example.teacherforboss.domain.model.exams.ExamResultWrongNotesEntity
import com.google.gson.annotations.SerializedName

data class ResponseExamResultWrongNotesDto(
    @SerializedName("examWrongQuestionList")
    val examWrongQuestionList:List<WrongQuestionDto>

){
    data class WrongQuestionDto(
        @SerializedName("questionSequence")
        val questionSequence:Int,
        @SerializedName("questionName")
        val questionName:String,
        @SerializedName("commentary")
        val commentary:String

    )


    fun toExamResultWrongNotesEntity():ExamResultWrongNotesEntity{
        val wrongQuestionEntities=examWrongQuestionList.map{
            ExamResultWrongNotesEntity.WrongQuestionEntity(
                questionSequence = it.questionSequence,
                questionName = it.questionName,
                commentary = it.commentary
            )
        }
        return ExamResultWrongNotesEntity(examWrongQuestionList=wrongQuestionEntities)
    }
}
