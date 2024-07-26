package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.auth.AccountEntity
import com.example.teacherforboss.domain.model.mypage.MyPageProfileEntity

interface MemberRepository {
    suspend fun getAccount():Result<AccountEntity>

    suspend fun getProfile():Result<MyPageProfileEntity>

}