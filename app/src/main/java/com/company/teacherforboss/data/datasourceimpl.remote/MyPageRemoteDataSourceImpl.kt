package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.MyPageRemoteDataSource
import com.company.teacherforboss.data.service.MyPageService
import javax.inject.Inject

class MyPageRemoteDataSourceImpl @Inject constructor(
    private val myPageService: MyPageService,
) : MyPageRemoteDataSource {
}