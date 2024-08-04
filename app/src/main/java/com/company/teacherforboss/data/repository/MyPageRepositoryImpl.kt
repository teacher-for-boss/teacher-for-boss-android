package com.company.teacherforboss.data.repository

import com.company.teacherforboss.data.datasource.remote.MemberRemoteDataSource
import com.company.teacherforboss.domain.repository.MemberRepository
import com.company.teacherforboss.domain.repository.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myPageRepository: MyPageRepository
):  MyPageRepository {
}