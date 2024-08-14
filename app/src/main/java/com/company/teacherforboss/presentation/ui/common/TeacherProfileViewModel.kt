package com.company.teacherforboss.presentation.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.common.TeacherProfileDetailEntity
import com.company.teacherforboss.domain.model.common.TeacherRecentAnswerListEntity.TeacherRecentAnswer
import com.company.teacherforboss.domain.model.common.TeacherDetailProfileRequestEntity
import com.company.teacherforboss.domain.model.common.TeacherRecentAnswerListEntity
import com.company.teacherforboss.domain.usecase.Member.TeacherDetailProfileUseCase
import com.company.teacherforboss.domain.usecase.Member.TeacherRecentAnswersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherProfileViewModel @Inject constructor(
    private val teacherDetailProfileUseCase: TeacherDetailProfileUseCase,
    private val teacherRecentAnswersUseCase: TeacherRecentAnswersUseCase
): ViewModel() {
    private val _teacherProfileDetail =
        MutableStateFlow<TeacherProfileDetailEntity?>(null)
    val teacherProfileDetail get() = _teacherProfileDetail.asStateFlow()

//    private val _teacherProfileRecentAnswerList =
//        MutableStateFlow<List<TeacherRecentAnswer>>(emptyList())

    private val _teacherProfileRecentAnswerList =
        MutableStateFlow(TeacherRecentAnswerListEntity(emptyList()))

    val teacherProfileRecentAnswerList get() = _teacherProfileRecentAnswerList.asStateFlow()

    var _memberId = MutableLiveData<Long>()
    val memberId: LiveData<Long> get() = _memberId

    fun getTeacherDetailProfile() {
        viewModelScope.launch {
            try {
                val teacherDetailProfileResponseEntity = teacherDetailProfileUseCase(
                    TeacherDetailProfileRequestEntity(
                        memberId = memberId.value
                    )
                )
                _teacherProfileDetail.value = teacherDetailProfileResponseEntity
            } catch (ex:Exception) {}
        }
    }

    fun getTeacherRecentAnswers() {
        viewModelScope.launch {
            try {
                val teacherRecentAnswersListResponseEntity = teacherRecentAnswersUseCase()
                _teacherProfileRecentAnswerList.value = teacherRecentAnswersListResponseEntity
            } catch (ex: Exception) {}
        }
    }

    fun setMemberId(id: Long) {
        _memberId.value = id
    }

//    fun setRecentAnswerList() {
//        _teacherProfileRecentAnswerList.value = listOf(
//            TeacherRecentAnswer(
//                questionId = 1,
//                questionTitle = "이런 질문 저런 질문 세상엔 참 많죠",
//                answer = "답변이라네요 제가 가장 최근에 남긴 답변은 이거에요",
//                answerLikeCount = "3",
//                answeredAt = "2024.07.17",
//            ),
//            TeacherRecentAnswer(
//                questionId = 2,
//                questionTitle = "최대한 긴 질문을 한번해볼게요 왜냐면 2줄 이상 넘어가면 안되거든요 얼마나 해야 넘어가려나? 아 이거보다 길어야 하네 꽤 많이 들어가네요 제 공기계가 커서 그런걸까요",
//                answer = "최대한 긴 답변도 한번 해볼게요 왜냐면 답변도 2줄 이상 넘어가면 안되거든요 디자인이 망가져요 얼마나 해야 넘어가징 조금만 더 하면",
//                answerLikeCount = "3",
//                answeredAt = "2024.07.17",
//            ),
//            TeacherRecentAnswer(
//                questionId = 3,
//                questionTitle = "안녕하세요",
//                answer = "답변이네요",
//                answerLikeCount = "3",
//                answeredAt = "2024.07.17",
//            ),
//            TeacherRecentAnswer(
//                questionId = 4,
//                questionTitle = "안녕하세요",
//                answer = "답변이네요",
//                answerLikeCount = "3",
//                answeredAt = "2024.07.17",
//            ),
//            TeacherRecentAnswer(
//                questionId = 5,
//                questionTitle = "안녕하세요",
//                answer = "답변이네요",
//                answerLikeCount = "3",
//                answeredAt = "2024.07.17",
//            ),
//            TeacherRecentAnswer(
//                questionId = 6,
//                questionTitle = "최대한 긴 질문을 한번해볼게요 왜냐면 2줄 이상 넘어가면 안되거든요 얼마나 해야 넘어가려나?",
//                answer = "최대한 긴 답변도 한번 해볼게요 왜냐면 답변도 2줄 이상 넘어가면 안되거든요 디자인이 망가져요 얼마나 해야 넘어가징",
//                answerLikeCount = "3",
//                answeredAt = "2024.07.17",
//            ),
//            TeacherRecentAnswer(
//                questionId = 7,
//                questionTitle = "최대한 긴 질문을 한번해볼게요 왜냐면 2줄 이상 넘어가면 안되거든요 얼마나 해야 넘어가려나?",
//                answer = "최대한 긴 답변도 한번 해볼게요 왜냐면 답변도 2줄 이상 넘어가면 안되거든요 디자인이 망가져요 얼마나 해야 넘어가징",
//                answerLikeCount = "3",
//                answeredAt = "2024.07.17",
//            ),
//        )
//    }
}
