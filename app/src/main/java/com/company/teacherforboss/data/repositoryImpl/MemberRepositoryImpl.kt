package com.company.teacherforboss.data.repositoryImpl

import com.company.teacherforboss.data.datasource.remote.MemberRemoteDataSource
import com.company.teacherforboss.domain.model.auth.AccountEntity
import com.company.teacherforboss.domain.model.common.TeacherProfileDetailEntity
import com.company.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.company.teacherforboss.domain.model.common.TeacherDetailProfileRequestEntity
import com.company.teacherforboss.domain.model.common.TeacherRecentAnswerListEntity
import com.company.teacherforboss.domain.model.mypage.ModifyBossProfileRequestEntity
import com.company.teacherforboss.domain.model.mypage.ModifyProfileResponseEntity
import com.company.teacherforboss.domain.model.mypage.ModifyTeacherProfileRequestEntity
import com.company.teacherforboss.domain.repository.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberRemoteDataSource: MemberRemoteDataSource
):MemberRepository {
    override suspend fun getAccount(): Result<AccountEntity> =
        runCatching { memberRemoteDataSource.getAccount().result.toAccountEntity() }

    override suspend fun getProfile(): Result<MyPageProfileEntity> =
        runCatching { memberRemoteDataSource.getProfile().result.toMyPageProfileEntity() }

    override suspend fun getTeacherDetailProfile(teacherDetailProfileRequestEntity: TeacherDetailProfileRequestEntity): TeacherProfileDetailEntity {
        return runCatching {
            memberRemoteDataSource.getTeacherDetailProfile(teacherDetailProfileRequestDto = teacherDetailProfileRequestEntity.toTeacherDetailProfileRequestDto())
                .result.toTeacherDetailProfileResponseEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun getTeacherRecentAnswers(teacherDetailProfileRequestEntity: TeacherDetailProfileRequestEntity): TeacherRecentAnswerListEntity {
        return runCatching {
            memberRemoteDataSource.getTeacherRecentAnswers(teacherDetailProfileRequestDto = teacherDetailProfileRequestEntity.toTeacherDetailProfileRequestDto())
                .result.toTeacherRecentAnswerListEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun modifyTeacherProfile(modifyTeacherProfileRequestEntity: ModifyTeacherProfileRequestEntity): ModifyProfileResponseEntity {
        return runCatching {
            memberRemoteDataSource.modifyTeacherProfile(modifyTeacherProfileRequestDto = modifyTeacherProfileRequestEntity.toModifyTeacherProfileRequestDto())
                .result.toModifyProfileResponseEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun modifyBossProfile(modifyBossProfileRequestEntity: ModifyBossProfileRequestEntity): ModifyProfileResponseEntity {
        return runCatching {
            memberRemoteDataSource.modifyBossProfile(modifyBossProfileRequestDto = modifyBossProfileRequestEntity.toModifyBossProfileRequestDto())
                .result.toModifyProfileResponseEntity()
        }.getOrElse { err -> throw err }
    }

}