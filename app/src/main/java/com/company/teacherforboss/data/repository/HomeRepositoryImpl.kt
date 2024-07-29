package com.company.teacherforboss.data.repository

import com.company.teacherforboss.data.datasource.remote.HomeRemoteDataSource
import com.company.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import com.company.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity
import com.company.teacherforboss.domain.model.home.WeeklyBestTeacherEntity
import com.company.teacherforboss.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource,
) : HomeRepository {
    override suspend fun getBossTalkPopularPost(): Result<List<BossTalkPopularPostEntity>> =
        runCatching {
            homeRemoteDataSource.getBossTalkPopularPost().result.hotPostList.map { it.toBossTalkPopularPostEntity() }
        }

    override suspend fun getTeacherTalkPopularPost(): Result<List<TeacherTalkPopularPostEntity>> =
        runCatching {
            homeRemoteDataSource.getTeacherTalkPopularPost().result.hotQuestionList.map { it.toTeacherTalkPopularPostEntity() }
        }

    override suspend fun getWeeklyBestTeacher(): Result<List<WeeklyBestTeacherEntity>> =
        runCatching {
            homeRemoteDataSource.getWeeklyBestTeacher().result.hotTeacherList.map { it.toWeeklyBestTeacherEntity() }
        }
}
