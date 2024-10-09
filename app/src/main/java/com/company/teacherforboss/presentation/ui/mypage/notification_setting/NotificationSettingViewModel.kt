package com.company.teacherforboss.presentation.ui.mypage.notification_setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.notification.NotificationSettingEntity
import com.company.teacherforboss.domain.usecase.notification.NotificationSettingUseCase
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationSettingViewModel @Inject constructor(
    private val notificationSettingUseCase: NotificationSettingUseCase
): ViewModel() {
    private val _getNotificationSettingState: MutableStateFlow<UiState<NotificationSettingEntity>> = MutableStateFlow(UiState.Empty)
    val getNotificationSettingState get() = _getNotificationSettingState.asStateFlow()

    fun getNotificationSetting() {
        viewModelScope.launch {
            notificationSettingUseCase().onSuccess { NotificationSettingEntity ->
                _getNotificationSettingState.value = UiState.Success(NotificationSettingEntity)
            }.onFailure { exception: Throwable ->
                _getNotificationSettingState.value = UiState.Error(exception.message)
            }
        }
    }
}