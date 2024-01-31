package com.example.teacherforboss.presentation.ui.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.presentation.type.SurveyType
import com.example.teacherforboss.util.combineAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SurveyViewModel : ViewModel() {
    private val _currentPage = MutableStateFlow(FIRST_FRAGMENT_POSITION)
    val currentPage get() = _currentPage.asStateFlow()

    private val _selectedJob = MutableStateFlow(DEFAULT_SELECTED_NUMBER)
    val selectedJob get() = _selectedJob.asStateFlow()

    private val _selectedStudy = MutableStateFlow(DEFAULT_SELECTED_NUMBER)
    val selectedStudy get() = _selectedStudy.asStateFlow()

    private val _selectedProblem = MutableStateFlow(DEFAULT_SELECTED_NUMBER)
    val selectedProblem get() = _selectedProblem.asStateFlow()

    private val _problemDescription = MutableStateFlow("")
    val problemDescription get() = _problemDescription.asStateFlow()

    val surveyBtnEnabled: StateFlow<Boolean> = listOf(
        currentPage,
        selectedJob,
        selectedStudy,
        selectedProblem,
        problemDescription,
    ).combineAll()
        .map { values ->
            val currentPage = values[0] as Int
            val selectedJob = values[1] as Int
            val selectedStudy = values[2] as Int
            val selectedProblem = values[3] as Int
            val problemDescription = values[4] as String

            (currentPage == SurveyType.JOB.position && selectedJob != DEFAULT_SELECTED_NUMBER && selectedJob != null) ||
                (currentPage == SurveyType.STUDY.position && selectedStudy != DEFAULT_SELECTED_NUMBER && selectedJob != null) ||
                (currentPage == SurveyType.PROBLEM.position && selectedProblem != DEFAULT_SELECTED_NUMBER && selectedJob != null) ||
                (currentPage == SurveyType.DESCRIPTION.position && problemDescription.isNotBlank()) ||
                (currentPage == SurveyType.COMPLETE.position)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    fun setSelectedJob(answer: Int) {
        _selectedJob.value = answer
    }

    fun setSelectedStudy(answer: Int) {
        _selectedStudy.value = answer
    }

    fun setSelectedProblem(answer: Int) {
        _selectedProblem.value = answer
    }

    fun setProblemDescription(answer: String) {
        _problemDescription.value = answer
    }

    companion object {
        private const val FIRST_FRAGMENT_POSITION = 0
        private const val DEFAULT_SELECTED_NUMBER = 0
    }
}
