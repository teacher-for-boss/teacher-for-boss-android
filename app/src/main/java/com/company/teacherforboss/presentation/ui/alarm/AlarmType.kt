package com.company.teacherforboss.presentation.ui.alarm

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.company.teacherforboss.R

enum class AlarmType(
    @DrawableRes val alarmIcon:Int,
    @StringRes val alarmType:Int
){
    TeacherTalk(
        alarmIcon = R.drawable.ic_alarm_teacher_talk,
        alarmType = R.string.alarm_type_teacher_talk
    ),
    BossTalk(
        alarmIcon = R.drawable.ic_alarm_boss_talk,
        alarmType = R.string.alarm_type_boss_talk
    ),
    Home(
        alarmIcon = R.drawable.ic_alarm_home,
        alarmType = R.string.alarm_type_home
    ),
    Event(
        alarmIcon=R.drawable.ic_alarm_event,
        alarmType=R.string.alarm_type_event
    ),
    Exchange(
        alarmIcon=R.drawable.ic_alarm_exchange_check,
        alarmType = R.string.alarm_type_exchange
    )

}
