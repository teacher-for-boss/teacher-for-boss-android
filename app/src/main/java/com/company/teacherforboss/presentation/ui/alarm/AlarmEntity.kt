package com.company.teacherforboss.presentation.ui.alarm

data class AlarmEntity(
    val alarmType: AlarmType,
    val alarmTitle:String,
    val alarmContent:String,
    val alarmTime:Int,
    val isClicked:Boolean
)
