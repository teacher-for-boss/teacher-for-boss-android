package com.company.teacherforboss.presentation.ui.notification

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

    val dummny_alarms= listOf(
        dummyNotificationEntity(
            notificationType = NotificationType.TeacherTalk,
            notificationTitle = "티처톡 알람 테스트 데이터 입니당",
            notificationContent = "~가 조회수를 50을 돌파했어요~",
            notificationTime = 5,
            isClicked = false
        ),
        dummyNotificationEntity(
            notificationType = NotificationType.BossTalk,
            notificationTitle = "보스톡 알람 테스트 데이터 입니당",
            notificationContent = "~가 조회수를 50을 돌파했어요~",
            notificationTime = 1,
            isClicked = true
        ),
        dummyNotificationEntity(
            notificationType = NotificationType.Home,
            notificationTitle = "홈 알람 테스트 데이터 입니당",
            notificationContent = "이번주 인기티처가 되셨군요~",
            notificationTime = 4,
            isClicked = false
        ),
        dummyNotificationEntity(
            notificationType = NotificationType.Exchange,
            notificationTitle = "환전완료 알람 테스트 데이터 입니당",
            notificationContent = "환전 완료~",
            notificationTime = 14,
            isClicked = false
        ),
        dummyNotificationEntity(
            notificationType = NotificationType.Event,
            notificationTitle = "중요이벤트 알람 테스트 데이터 입니당",
            notificationContent = "중요 이벤트~~~",
            notificationTime = 7,
            isClicked = false
        ),


        dummyNotificationEntity(
            notificationType = NotificationType.TeacherTalk,
            notificationTitle = "티처톡 알람 테스트 데이터 입니당",
            notificationContent = "~가 조회수를 50을 돌파했어요~",
            notificationTime = 5,
            isClicked = false
        ),
        dummyNotificationEntity(
            notificationType = NotificationType.BossTalk,
            notificationTitle = "보스톡 알람 테스트 데이터 입니당",
            notificationContent = "~가 조회수를 50을 돌파했어요~",
            notificationTime = 1,
            isClicked = true
        ),
        dummyNotificationEntity(
            notificationType = NotificationType.Home,
            notificationTitle = "홈 알람 테스트 데이터 입니당",
            notificationContent = "이번주 인기티처가 되셨군요~",
            notificationTime = 4,
            isClicked = false
        ),
        dummyNotificationEntity(
            notificationType = NotificationType.Exchange,
            notificationTitle = "환전완료 알람 테스트 데이터 입니당",
            notificationContent = "환전 완료~",
            notificationTime = 14,
            isClicked = false
        ),
        dummyNotificationEntity(
            notificationType = NotificationType.Event,
            notificationTitle = "중요이벤트 알람 테스트 데이터 입니당",
            notificationContent = "중요 이벤트~~~",
            notificationTime = 7,
            isClicked = false
        ),
    )

    private val _notificationState= MutableStateFlow<UiState<NotificationListEntity>>(UiState.Empty)
    val notificationState=_notificationState.asStateFlow()

    fun getNotificatioins(){
       viewModelScope.launch {
           notificationUseCase().onSuccess {notificationListEntity ->
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

}