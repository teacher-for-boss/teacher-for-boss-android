package com.company.teacherforboss.presentation.ui.notification

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.company.teacherforboss.R

enum class NotificationType(
    @DrawableRes val notificationIcon:Int,
    @StringRes val notificationType:Int
){
    TeacherTalk(
        notificationIcon = R.drawable.ic_notification_teacher_talk,
        notificationType = R.string.notification_type_teacher_talk
    ),
    BossTalk(
        notificationIcon = R.drawable.ic_notification_boss_talk,
        notificationType = R.string.notification_type_boss_talk
    ),
    Home(
        notificationIcon = R.drawable.ic_notification_home,
        notificationType = R.string.notification_type_home
    ),
    Event(
        notificationIcon=R.drawable.ic_notification_event,
        notificationType=R.string.notification_type_event
    ),
    Exchange(
        notificationIcon=R.drawable.ic_notification_check,
        notificationType = R.string.notification_type_exchange
    )

}
