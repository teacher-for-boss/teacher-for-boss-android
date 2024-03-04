package com.example.teacherforboss.domain.model.exams

data class ExamResultResultEntity(
    val score:Int,
    val questionsNum:Int,
    val correctAnsNum:Int,
    val incorrectAnsNum:Int
)
