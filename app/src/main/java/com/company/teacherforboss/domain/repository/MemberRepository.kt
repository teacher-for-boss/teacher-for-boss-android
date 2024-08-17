package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.auth.AccountEntity
import com.company.teacherforboss.domain.model.common.TeacherProfileDetailEntity
import com.company.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.company.teacherforboss.domain.model.common.TeacherDetailProfileRequestEntity
import com.company.teacherforboss.domain.model.common.TeacherRecentAnswerListEntity
import com.company.teacherforboss.domain.model.mypage.ModifyBossProfileRequestEntity
import com.company.teacherforboss.domain.model.mypage.ModifyProfileResponseEntity
import com.company.teacherforboss.domain.model.mypage.ModifyTeacherProfileRequestEntity

interface MemberRepository {
    suspend fun getAccount():Result<AccountEntity>

    suspend fun getProfile():Result<MyPageProfileEntity>

    suspend fun getTeacherDetailProfile(teacherDetailProfileRequestEntity: TeacherDetailProfileRequestEntity): TeacherProfileDetailEntity

    suspend fun getTeacherRecentAnswers(): TeacherRecentAnswerListEntity

    suspend fun modifyTeacherProfile(modifyTeacherProfileRequestEntity: ModifyTeacherProfileRequestEntity): ModifyProfileResponseEntity

    suspend fun modifyBossProfile(modifyBossProfileRequestEntity: ModifyBossProfileRequestEntity): ModifyProfileResponseEntity

}