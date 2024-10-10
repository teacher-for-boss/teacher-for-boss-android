package com.company.teacherforboss.presentation.ui.mypage.notification_setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.notification.NotificationSettingEntity
import com.company.teacherforboss.domain.usecase.notification.NotificationSettingGetUseCase
import com.company.teacherforboss.domain.usecase.notification.NotificationSettingPostUseCase
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationSettingViewModel @Inject constructor(
    private val notificationSettingUseCase: NotificationSettingGetUseCase,
    private val notificationSettingPostUseCase: NotificationSettingPostUseCase
): ViewModel() {
    private val _getNotificationSettingState: MutableStateFlow<UiState<NotificationSettingEntity>> = MutableStateFlow(UiState.Empty)
    val getNotificationSettingState get() = _getNotificationSettingState.asStateFlow()

    private val _postNotificationSettingState: MutableStateFlow<UiState<NotificationSettingEntity>> = MutableStateFlow(UiState.Empty)
    val postNotificationSettingState get() = _postNotificationSettingState.asStateFlow()

    private val _serviceNotification =  MutableLiveData<Boolean>(false)
    val serviceNotification: LiveData<Boolean> get() = _serviceNotification

    private val _marketingNotificationPush =  MutableLiveData<Boolean>(false)
    val marketingNotificationPush: LiveData<Boolean> get() = _marketingNotificationPush

    private val _marketingNotificationEmail =  MutableLiveData<Boolean>(false)
    val marketingNotificationEmail: LiveData<Boolean> get() = _marketingNotificationEmail

    private val _marketingNotificationSMS =  MutableLiveData<Boolean>(false)
    val marketingNotificationSMS: LiveData<Boolean> get() = _marketingNotificationSMS

    fun getNotificationSetting() {
        viewModelScope.launch {
            notificationSettingUseCase().onSuccess { NotificationSettingEntity ->
                _getNotificationSettingState.value = UiState.Success(NotificationSettingEntity)
            }.onFailure { exception: Throwable ->
                _getNotificationSettingState.value = UiState.Error(exception.message)
            }
        }
    }

    fun postNotificationSetting() {
        viewModelScope.launch {
            notificationSettingPostUseCase(
                notificationSettingEntity = NotificationSettingEntity(
                    serviceNotification = serviceNotification.value!!,
                    marketingNotification = NotificationSettingEntity.MarketingNotificationSettingEntity(
                        push = marketingNotificationPush.value!!,
                        email = marketingNotificationEmail.value!!,
                        sms = marketingNotificationSMS.value!!
                    )
                )
            ).onSuccess { NotificationSettingEntity ->
                _postNotificationSettingState.value = UiState.Success(NotificationSettingEntity)
            }.onFailure { exception: Throwable ->
                _postNotificationSettingState.value = UiState.Error(exception.message)
            }
        }

    }

    fun setServiceNotification(value: Boolean) {
        _serviceNotification.value = value
    }
    fun setMarketingPush(value: Boolean) {
        _marketingNotificationPush.value = value
    }
    fun setMarketingEmail(value: Boolean) {
        _marketingNotificationEmail.value = value
    }
    fun setMarketingSMS(value: Boolean) {
        _marketingNotificationSMS.value = value
    }
}