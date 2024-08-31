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
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_ID
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
                        memberId = memberId.value?:DEFAULT_ID
                    )
                )
                _teacherProfileDetail.value = teacherDetailProfileResponseEntity
            } catch (ex:Exception) {}
        }
    }

    fun getTeacherRecentAnswers() {
        viewModelScope.launch {
            try {
                val teacherRecentAnswersListResponseEntity = teacherRecentAnswersUseCase(
                    TeacherDetailProfileRequestEntity(
                        memberId = memberId.value?: DEFAULT_ID
                    )
                )
                _teacherProfileRecentAnswerList.value = teacherRecentAnswersListResponseEntity
            } catch (ex: Exception) {}
        }
    }

    fun setMemberId(id: Long) {
        _memberId.value = id
    }
    fun getMembeerId()=memberId.value
}
