package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.HomeRemoteDataSource
import com.example.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import com.example.teacherforboss.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource,
) : HomeRepository {
    override suspend fun getBossTalkPopularPost(): Result<List<BossTalkPopularPostEntity>> =
        runCatching {
            homeRemoteDataSource.getBossTalkPopularPost().result.hotPostList.map { it.toBossTalkPopularPostEntity() }
        }
}
