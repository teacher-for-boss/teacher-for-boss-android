package com.company.teacherforboss.presentation.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.notification.NotificationListEntity
import com.company.teacherforboss.domain.model.notification.NotificationReadEntity
import com.company.teacherforboss.domain.usecase.notification.NotificationUseCase
import com.company.teacherforboss.domain.usecase.notification.ReadNotificationUseCase
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationUseCase: NotificationUseCase,
    private val readNotificationUseCase: ReadNotificationUseCase
): ViewModel() {
    private val _notificationState= MutableStateFlow<UiState<NotificationListEntity>>(UiState.Empty)
    val notificationState=_notificationState.asStateFlow()

    var _lastNotificationId = MutableLiveData<Long>(0)
    val lastNotificationId: LiveData<Long> get() = _lastNotificationId

    fun getNotifications(){
       viewModelScope.launch {
           notificationUseCase(lastNotificationId = getLastPostId()).onSuccess {notificationListEntity ->
               _notificationState.value=UiState.Success(notificationListEntity)
           }.onFailure { exception: Throwable ->
               _notificationState.value=UiState.Error(exception.message)
           }
       }
    }

    private val _readNotificationState= MutableStateFlow<UiState<NotificationReadEntity>>(UiState.Empty)
    val readNotificationState=_readNotificationState.asStateFlow()

    fun readNotification(notificationId:Long){
        viewModelScope.launch {
            readNotificationUseCase(notificationId).onSuccess { readState->
                _readNotificationState.value=UiState.Success(readState)
            }.onFailure {
                _readNotificationState.value=UiState.Error(it.message)
            }
        }
    }

    fun setLastPostId(id: Long) {
        _lastNotificationId.value = id
    }

    fun getLastPostId(): Long {
        return lastNotificationId.value!!
    }
}