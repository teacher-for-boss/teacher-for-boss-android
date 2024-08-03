package com.company.teacherforboss.presentation.ui.alarm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(): ViewModel() {
    val dummny_alarms= listOf(
        AlarmEntity(
            alarmType = AlarmType.TeacherTalk,
            alarmTitle = "티처톡 알람 테스트 데이터 입니당",
            alarmContent = "~가 조회수를 50을 돌파했어요~",
            alarmTime = 5,
            isClicked = false
        ),
        AlarmEntity(
            alarmType = AlarmType.BossTalk,
            alarmTitle = "보스톡 알람 테스트 데이터 입니당",
            alarmContent = "~가 조회수를 50을 돌파했어요~",
            alarmTime = 1,
            isClicked = true
        ),
        AlarmEntity(
            alarmType = AlarmType.Home,
            alarmTitle = "홈 알람 테스트 데이터 입니당",
            alarmContent = "이번주 인기티처가 되셨군요~",
            alarmTime = 4,
            isClicked = false
        ),
        AlarmEntity(
            alarmType = AlarmType.Exchange,
            alarmTitle = "환전완료 알람 테스트 데이터 입니당",
            alarmContent = "환전 완료~",
            alarmTime = 14,
            isClicked = false
        ),
        AlarmEntity(
            alarmType = AlarmType.Event,
            alarmTitle = "중요이벤트 알람 테스트 데이터 입니당",
            alarmContent = "중요 이벤트~~~",
            alarmTime = 7,
            isClicked = false
        ),
        AlarmEntity(
            alarmType = AlarmType.TeacherTalk,
            alarmTitle = "티처톡 알람 테스트 데이터 입니당",
            alarmContent = "~가 조회수를 150을 돌파했어요~",
            alarmTime = 15,
            isClicked = false
        ),
        AlarmEntity(
            alarmType = AlarmType.BossTalk,
            alarmTitle = "보스톡 알람 테스트 데이터 입니당",
            alarmContent = "~가 조회수를 100을 돌파했어요~",
            alarmTime = 11,
            isClicked = true
        ),
        AlarmEntity(
            alarmType = AlarmType.Event,
            alarmTitle = "중요이벤트 알람 테스트 데이터 입니당",
            alarmContent = "중요 이벤트~~~",
            alarmTime = 7,
            isClicked = false
        ),
        AlarmEntity(
            alarmType = AlarmType.TeacherTalk,
            alarmTitle = "티처톡 알람 테스트 데이터 입니당",
            alarmContent = "~가 조회수를 150을 돌파했어요~",
            alarmTime = 15,
            isClicked = false
        ),
    )

}