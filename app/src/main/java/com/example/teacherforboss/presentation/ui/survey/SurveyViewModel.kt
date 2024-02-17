package com.example.teacherforboss.presentation.ui.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.SurveyResultEntity
import com.example.teacherforboss.presentation.type.SurveyType
import com.example.teacherforboss.util.combineAll
import com.example.teacherforboss.util.view.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// @HiltViewModel
class SurveyViewModel(
//    private val postSurveyUseCase: PostSurveyUseCase
) : ViewModel() {
    private val _currentPage = MutableStateFlow(FIRST_FRAGMENT_POSITION)
    val currentPage get() = _currentPage.asStateFlow()

    private val _selectedJob = MutableStateFlow(DEFAULT_SELECTED_NUMBER)
    val selectedJob get() = _selectedJob.asStateFlow()

    private val _selectedStudy = MutableStateFlow<ArrayList<Int>>(arrayListOf())
    val selectedStudy get() = _selectedStudy.asStateFlow()

    private val _selectedProblem = MutableStateFlow(DEFAULT_SELECTED_NUMBER)
    val selectedProblem get() = _selectedProblem.asStateFlow()

    val problemDescription = MutableStateFlow("")

    private val _surveyResultState = MutableSharedFlow<UiState<SurveyResultEntity>>()
    val surveyResultState get() = _surveyResultState.asSharedFlow()

    private val selectedStudySize = MutableStateFlow<Int>(0)

    val surveyBtnEnabled: StateFlow<Boolean> = listOf(
        currentPage,
        selectedJob,
        selectedStudySize,
        selectedProblem,
        problemDescription,
    ).combineAll()
        .map { values ->
            val currentPage = values[0] as Int
            val selectedJob = values[1] as Int
            val selectedStudySize = values[2] as Int
            val selectedProblem = values[3] as Int
            val problemDescription = values[4] as String

            (currentPage == SurveyType.JOB.position && selectedJob != DEFAULT_SELECTED_NUMBER && selectedJob != null) ||
                (currentPage == SurveyType.STUDY.position && selectedStudySize != 0) ||
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
        _selectedStudy.value.add(answer)
    }

    fun setSelectedStudySize() {
        selectedStudySize.value = _selectedStudy.value.size
    }

    fun deleteSelectedStudy(answer: Int) {
        if (_selectedStudy.value.size == 1) _selectedStudy.value.clear()
        _selectedStudy.value.remove(answer)
        setSelectedStudySize()
    }

    fun setSelectedProblem(answer: Int) {
        _selectedProblem.value = answer
        setSelectedStudySize()
    }

    //    fun postSurveyResult() {
//        viewModelScope.launch {
//            _surveyResultState.emit(UiState.Loading)
//            runCatching {
//                postSurveyUseCase(
//                    surveyEntity = SurveyEntity(
//                        question1 = selectedJob.value,
//                        question2 = selectedStudy.value,
//                        question3 = selectedProblem.value,
//                        question4 = problemDescription.value
//                    )
//                ).collect(){data ->
//                    _surveyResultState.emit(UiState.Success(data))
//                }
//            }.onFailure { exception: Throwable ->
//                _surveyResultState.emit(UiState.Error(exception.message))
//            }
//        }
//    }
    companion object {
        private const val FIRST_FRAGMENT_POSITION = 0
        private const val DEFAULT_SELECTED_NUMBER = 0
    }
}
