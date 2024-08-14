package com.company.teacherforboss.data.model.response.mypage

import com.company.teacherforboss.data.model.response.community.teacher.QuestionDto
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseMyPageAnsweredQuestionDto (
    @SerializedName("hasNext")
    val hasNext:Boolean,
    @SerializedName("answeredQuestionList")
    val answeredQuestionList: ArrayList<MyPageQuestionDto>?,
    @SerializedName("questionList")
    val questionList: ArrayList<MyPageQuestionDto>?
){
    fun toMyPageAnsweredQuestionResponseEntity(): MyPageAnsweredQuestionResponseEntity {
        val answeredQuestionList = answeredQuestionList?.mapTo(ArrayList()) {it.toMyPageQuestionEntity()}
        val questionList = questionList?.mapTo(ArrayList()) {it.toMyPageQuestionEntity()}

        return MyPageAnsweredQuestionResponseEntity(
            hasNext = hasNext,
            answeredQuestionList=answeredQuestionList,
            questionList=questionList
        )
    }
}