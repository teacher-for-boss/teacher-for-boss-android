package com.company.teacherforboss.data.repository

import com.company.teacherforboss.data.datasource.remote.SignupRemoteDataSource
import com.company.teacherforboss.data.mapper.toRequestSignupDto
import com.company.teacherforboss.domain.model.signup.SignupEntity
import com.company.teacherforboss.domain.model.signup.SignupResultEntity
import com.company.teacherforboss.domain.repository.SignupRepository
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