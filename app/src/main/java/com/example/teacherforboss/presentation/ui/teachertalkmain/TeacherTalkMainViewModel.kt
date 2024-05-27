package com.example.teacherforboss.presentation.ui.teachertalkmain.basic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teacherforboss.presentation.ui.teachertalkmain.card.TeacherTalkCard

class TeacherTalkMainViewModel : ViewModel() {

    private val _teacherTalkCards = MutableLiveData<List<TeacherTalkCard>>()
    val teacherTalkCards: LiveData<List<TeacherTalkCard>> get() = _teacherTalkCards

    init {
        loadTeacherTalkCards()
    }

    private fun loadTeacherTalkCards() {
        // 여기서 데이터를 로드하거나 Mock 데이터를 설정합니다.
        _teacherTalkCards.value = listOf(
            TeacherTalkCard(
                question = "1번",
                answer="1번 a",
                statement_answer = "1s",
                count_bookmark="1c",
                count_like="1cl",
                count_comment="1cc",
            ),
            TeacherTalkCard(
                question = "2번",
                answer="2번 a",
                statement_answer = "2s",
                count_bookmark="2c",
                count_like="2cl",
                count_comment="2cc",
            ),
            TeacherTalkCard(
                question = "3번",
                answer="3번 a",
                statement_answer = "3s",
                count_bookmark="3c",
                count_like="3cl",
                count_comment="3cc",
            ),
            // 추가 데이터
        )
    }
}
