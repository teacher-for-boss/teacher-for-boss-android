package com.company.teacherforboss.presentation.ui.notification

import android.app.Activity
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.presentation.ui.mypage.exchange.ExchangeHistoryActivity
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_POSTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_QUESTIONID

enum class NotificationNavigationType(val destinationActivity: Class<out Activity>,val id:String?=null) {
    QUESTION(TeacherTalkBodyActivity::class.java, TEACHER_QUESTIONID),
    POST(BossTalkBodyActivity::class.java, BOSS_POSTID),
    HOME(MainActivity::class.java),
    EXCHANGE(ExchangeHistoryActivity::class.java),
    QUESTION_AUTO_DELETE(MainActivity::class.java);

    companion object{
        fun from(type:String):NotificationNavigationType?{
            return values().find{it.name==type}
        }
    }
}
