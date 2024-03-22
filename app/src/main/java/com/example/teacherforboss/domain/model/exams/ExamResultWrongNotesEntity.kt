package com.example.teacherforboss.domain.model.exams

data class ExamResultWrongNotesEntity(
    val examWrongQuestionList:List<WrongQuestionEntity>
){
    data class WrongQuestionEntity(
        val questionId:Long,
        val questionSequence:Int,
        val questionName:String
    )

}

