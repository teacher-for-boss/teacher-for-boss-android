package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.auth.AccountEntity

interface MemberRepository {
    suspend fun getAccount():Result<AccountEntity>

}