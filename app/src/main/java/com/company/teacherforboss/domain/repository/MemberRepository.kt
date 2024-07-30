package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.auth.AccountEntity
import com.company.teacherforboss.domain.model.mypage.MyPageProfileEntity

interface MemberRepository {
    suspend fun getAccount():Result<AccountEntity>

    suspend fun getProfile():Result<MyPageProfileEntity>

}