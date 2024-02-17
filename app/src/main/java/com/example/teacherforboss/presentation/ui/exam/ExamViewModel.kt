package com.example.teacherforboss.presentation.ui.exam

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.presentation.type.ExamType
import com.example.teacherforboss.util.combineAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Locale

class ExamViewModel : ViewModel() {
    private val _currentPage = MutableStateFlow(FIRST_FRAGMENT_POSITION)
    val currentPage get() = _currentPage.asStateFlow()

    private val _currentTime = MutableStateFlow(DEFAULT_TIME)
    val currentTime get() = _currentTime.asStateFlow()

    private var countDownTimer: CountDownTimer? = null

    private val _selectedFirstAnswer = MutableStateFlow(DEFAULT_SELECTED_NUMBER)
    val selectedFirstAnswer get() = _selectedFirstAnswer.asStateFlow()

    private val _selectedSecondAnswer = MutableStateFlow(DEFAULT_SELECTED_NUMBER)
    val selectedSecondAnswer get() = _selectedSecondAnswer.asStateFlow()

    private val _selectedThirdAnswer = MutableStateFlow(DEFAULT_SELECTED_NUMBER)
    val selectedThirdAnswer get() = _selectedThirdAnswer.asStateFlow()

    private val _selectedFourthAnswer = MutableStateFlow(DEFAULT_SELECTED_NUMBER)
    val selectedFourthAnswer get() = _selectedFourthAnswer.asStateFlow()

    init {
        startTimer()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(DEFAULT_TIME_IN_MILLIS, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
            }

            override fun onFinish() {
                // Timer 종료
            }
        }.start()
    }

    private fun updateTimerText(millisIntilFinished: Long) {
        val seconds = millisIntilFinished / 1000
        val formattedTime = String.format(
            Locale.getDefault(),
            "%02d:%02d",
            seconds / 60,
            seconds % 60,
        )
        _currentTime.value = formattedTime
    }

    val examBtnEnabled: StateFlow<Boolean> = listOf(
        currentPage,
        selectedFirstAnswer,
        selectedSecondAnswer,
        selectedThirdAnswer,
        selectedFourthAnswer,
    ).combineAll().map { values ->
        val currentPage = values[0]
        val selectedFirstAnswer = values[1]
        val selectedSecondAnswer = values[2]
        val selectedThirdAnswer = values[3]
        val selectedFourthAnswer = values[4]

        (currentPage == ExamType.FIRST.position && selectedFirstAnswer != 0) ||
            (currentPage == ExamType.SECOND.position && selectedSecondAnswer != 0) ||
            (currentPage == ExamType.THIRD.position && selectedThirdAnswer != 0) ||
            (currentPage == ExamType.FOURTH.position && selectedFourthAnswer != 0)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    fun setSelectedFirstAnswer(answer: Int) {
        _selectedFirstAnswer.value = answer
    }

    fun setSelectedSecondAnswer(answer: Int) {
        _selectedSecondAnswer.value = answer
    }

    fun setSelectedThirdAnswer(answer: Int) {
        _selectedThirdAnswer.value = answer
    }

    fun setSelectedFourthAnswer(answer: Int) {
        _selectedFourthAnswer.value = answer
    }

    companion object {
        private const val FIRST_FRAGMENT_POSITION = 0
        private const val DEFAULT_TIME = "10:00"
        private const val DEFAULT_SELECTED_NUMBER = 0
        private const val DEFAULT_TIME_IN_MILLIS = 10 * 60 * 1000L
    }
}
