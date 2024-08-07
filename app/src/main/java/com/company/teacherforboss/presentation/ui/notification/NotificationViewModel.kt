package com.company.teacherforboss.presentation.ui.notification

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(): ViewModel() {
    val dummny_alarms= listOf(
        NotificationEntity(
            notificationType = NotificationType.TeacherTalk,
            notificationTitle = "티처톡 알람 테스트 데이터 입니당",
            notificationContent = "~가 조회수를 50을 돌파했어요~",
            notificationTime = 5,
            isClicked = false
        ),
        NotificationEntity(
            notificationType = NotificationType.BossTalk,
            notificationTitle = "보스톡 알람 테스트 데이터 입니당",
            notificationContent = "~가 조회수를 50을 돌파했어요~",
            notificationTime = 1,
            isClicked = true
        ),
        NotificationEntity(
            notificationType = NotificationType.Home,
            notificationTitle = "홈 알람 테스트 데이터 입니당",
            notificationContent = "이번주 인기티처가 되셨군요~",
            notificationTime = 4,
            isClicked = false
        ),
        NotificationEntity(
            notificationType = NotificationType.Exchange,
            notificationTitle = "환전완료 알람 테스트 데이터 입니당",
            notificationContent = "환전 완료~",
            notificationTime = 14,
            isClicked = false
        ),
        NotificationEntity(
            notificationType = NotificationType.Event,
            notificationTitle = "중요이벤트 알람 테스트 데이터 입니당",
            notificationContent = "중요 이벤트~~~",
            notificationTime = 7,
            isClicked = false
        ),


        NotificationEntity(
            notificationType = NotificationType.TeacherTalk,
            notificationTitle = "티처톡 알람 테스트 데이터 입니당",
            notificationContent = "~가 조회수를 50을 돌파했어요~",
            notificationTime = 5,
            isClicked = false
        ),
        NotificationEntity(
            notificationType = NotificationType.BossTalk,
            notificationTitle = "보스톡 알람 테스트 데이터 입니당",
            notificationContent = "~가 조회수를 50을 돌파했어요~",
            notificationTime = 1,
            isClicked = true
        ),
        NotificationEntity(
            notificationType = NotificationType.Home,
            notificationTitle = "홈 알람 테스트 데이터 입니당",
            notificationContent = "이번주 인기티처가 되셨군요~",
            notificationTime = 4,
            isClicked = false
        ),
        NotificationEntity(
            notificationType = NotificationType.Exchange,
            notificationTitle = "환전완료 알람 테스트 데이터 입니당",
            notificationContent = "환전 완료~",
            notificationTime = 14,
            isClicked = false
        ),
        NotificationEntity(
            notificationType = NotificationType.Event,
            notificationTitle = "중요이벤트 알람 테스트 데이터 입니당",
            notificationContent = "중요 이벤트~~~",
            notificationTime = 7,
            isClicked = false
        ),
    )

}