package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.auth.AccountEntity
import com.company.teacherforboss.domain.model.common.TeacherProfileDetailEntity
import com.company.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.company.teacherforboss.domain.model.common.TeacherDetailProfileRequestEntity

interface MemberRepository {
    suspend fun getAccount():Result<AccountEntity>

    suspend fun getProfile():Result<MyPageProfileEntity>

    suspend fun getTeacherDetailProfile(teacherDetailProfileRequestEntity: TeacherDetailProfileRequestEntity): TeacherProfileDetailEntity

}