package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.SignupRemoteDataSource
import com.example.teacherforboss.data.mapper.toRequestSignupDto
import com.example.teacherforboss.domain.model.SignupEntity
import com.example.teacherforboss.domain.model.SignupResultEntity
import com.example.teacherforboss.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//data:datasource, domain:repository
class SignupRepositoryImpl @Inject constructor(
    private val signupRemoteDataSource: SignupRemoteDataSource
) :SignupRepository{
    override suspend fun signup(signupEntity: SignupEntity): Flow<SignupResultEntity> = flow{

        val data= kotlin.runCatching {
            signupRemoteDataSource.signup(requestSignupDto = signupEntity.toRequestSignupDto()
            ).result.toSignupResultEntity()
        }
        emit(data.getOrThrow())
    }

}