package com.company.teacherforboss.data.model.response.mypage

import com.company.teacherforboss.data.model.response.community.teacher.QuestionDto
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseMyPageAnsweredQuestionDto (
    @SerializedName("hasNext")
    val hasNext:Boolean,
    @SerializedName("questionList")
    val questionList: ArrayList<MyPageQuestionDto>
){
    fun toMyPageAnsweredQuestionResponseEntity(): MyPageAnsweredQuestionResponseEntity {
        val questionList = questionList.mapTo(ArrayList()) {it.toMyPageQuestionEntity()}

        return MyPageAnsweredQuestionResponseEntity(
            hasNext = hasNext,
            questionList=questionList
        )
    }
}