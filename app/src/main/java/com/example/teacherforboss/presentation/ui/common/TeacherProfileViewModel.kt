package com.example.teacherforboss.presentation.ui.common

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.example.teacherforboss.domain.model.common.TeacherProfileDetailEntity
import com.example.teacherforboss.domain.model.common.TeacherRecentAnswerListEntity.TeacherRecentAnswer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TeacherProfileViewModel : ViewModel() {
    private val _teacherProfileDetail =
        MutableStateFlow<TeacherProfileDetailEntity?>(null)
    val teacherProfileDetail get() = _teacherProfileDetail.asStateFlow()

    private val _teacherProfileRecentAnswerList =
        MutableStateFlow<List<TeacherRecentAnswer>>(emptyList())
    val teacherProfileRecentAnswerList get() = _teacherProfileRecentAnswerList.asStateFlow()

    fun setTeacherProfileDetail() {
        _teacherProfileDetail.value = TeacherProfileDetailEntity(
            nickname = "하지은이지롱",
            profileImgUrl = "https://img-cdn.theqoo.net/bJgQuT.jpg",
            information = "저는 엄청난 자영업 컨설턴트입니다. 믿고 맡겨주시라!",
            phoneNum = "010-1111-2222",
            email = "abc@gmail.com",
            specialty = "자영업 컨설턴트 전문, 인테리어 전문",
            career = 16,
            keyword = listOf("혁신적인", "전문적인", "섬세한"),
            level = "Lv.1 지식의 별",
            isMine = true,
            isSelected = ObservableBoolean(false),
        )
    }

    fun setRecentAnswerList() {
        _teacherProfileRecentAnswerList.value = listOf(
            TeacherRecentAnswer(
                questionId = 1,
                questionTitle = "이런 질문 저런 질문 세상엔 참 많죠",
                answer = "답변이라네요 제가 가장 최근에 남긴 답변은 이거에요",
                answerLikeCount = "3",
                answeredAt = "2024.07.17",
            ),
            TeacherRecentAnswer(
                questionId = 2,
                questionTitle = "최대한 긴 질문을 한번해볼게요 왜냐면 2줄 이상 넘어가면 안되거든요 얼마나 해야 넘어가려나? 아 이거보다 길어야 하네 꽤 많이 들어가네요 제 공기계가 커서 그런걸까요",
                answer = "최대한 긴 답변도 한번 해볼게요 왜냐면 답변도 2줄 이상 넘어가면 안되거든요 디자인이 망가져요 얼마나 해야 넘어가징 조금만 더 하면",
                answerLikeCount = "3",
                answeredAt = "2024.07.17",
            ),
            TeacherRecentAnswer(
                questionId = 3,
                questionTitle = "안녕하세요",
                answer = "답변이네요",
                answerLikeCount = "3",
                answeredAt = "2024.07.17",
            ),
            TeacherRecentAnswer(
                questionId = 4,
                questionTitle = "안녕하세요",
                answer = "답변이네요",
                answerLikeCount = "3",
                answeredAt = "2024.07.17",
            ),
            TeacherRecentAnswer(
                questionId = 5,
                questionTitle = "안녕하세요",
                answer = "답변이네요",
                answerLikeCount = "3",
                answeredAt = "2024.07.17",
            ),
            TeacherRecentAnswer(
                questionId = 6,
                questionTitle = "최대한 긴 질문을 한번해볼게요 왜냐면 2줄 이상 넘어가면 안되거든요 얼마나 해야 넘어가려나?",
                answer = "최대한 긴 답변도 한번 해볼게요 왜냐면 답변도 2줄 이상 넘어가면 안되거든요 디자인이 망가져요 얼마나 해야 넘어가징",
                answerLikeCount = "3",
                answeredAt = "2024.07.17",
            ),
            TeacherRecentAnswer(
                questionId = 7,
                questionTitle = "최대한 긴 질문을 한번해볼게요 왜냐면 2줄 이상 넘어가면 안되거든요 얼마나 해야 넘어가려나?",
                answer = "최대한 긴 답변도 한번 해볼게요 왜냐면 답변도 2줄 이상 넘어가면 안되거든요 디자인이 망가져요 얼마나 해야 넘어가징",
                answerLikeCount = "3",
                answeredAt = "2024.07.17",
            ),
        )
    }
}
