package com.example.teacherforboss.presentation.ui.survey

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SurveyViewModel : ViewModel() {
    // val SurveyBtnEnabled: StateFlow<Boolean> = true

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
