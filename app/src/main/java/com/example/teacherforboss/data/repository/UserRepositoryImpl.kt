package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.service.UserApi
import com.example.teacherforboss.data.model.request.LoginRequest
import com.example.teacherforboss.data.model.request.SocialLoginRequest
import com.example.teacherforboss.data.model.response.LoginResponse
import com.example.teacherforboss.data.model.response.socialLoginResponse
import com.example.teacherforboss.domain.repository.UserRepository
import com.example.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.example.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.example.teacherforboss.data.model.request.signup.EmailRequest
import com.example.teacherforboss.data.model.response.signup.EmailResponse
import com.example.teacherforboss.data.model.request.signup.SignupRequest
import com.example.teacherforboss.data.model.response.signup.SignupResponse
import com.example.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.example.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.example.teacherforboss.data.model.request.signup.PhoneRequest
import com.example.teacherforboss.data.model.response.signup.PhoneResponse
import retrofit2.Response

//class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource):UserRepository {
//    override suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
//        return userRemoteDataSource.loginUser(loginRequest)
//    }
//
//    override fun loginReissue(refreshToken: String): Response<LoginResponse>? {
//        return userRemoteDataSource.loginReissue(refreshToken ="Bearer ${refreshToken}" )
//    }
//
//    override suspend fun kakaoLogin(socialLoginRequest: socialLoginRequest): Response<socialLoginResponse>? {
//        return UserApi.getApi()?.socialLogin(socialType=2, socialLoginRequest=socialLoginRequest)
//    }
//
//    override suspend fun naverLogin(socialLoginRequest: socialLoginRequest): Response<socialLoginResponse>? {
//        return UserApi.getApi()?.socialLogin(socialType=3, socialLoginRequest=socialLoginRequest)
//    }
//
//    override suspend fun emailUser(emailRequest: EmailRequest): Response<EmailResponse>? {
//        return userRemoteDataSource.emailUser(emailRequest)
//    }
//
//    override suspend fun emailCheck(emailCheckRequest: EmailCheckRequest): Response<EmailCheckResponse>? {
//        return userRemoteDataSource.emailCheck(emailCheckRequest)
//    }
//
//    override suspend fun signupUser(signupRequest: SignupRequest): Response<SignupResponse>? {
//        return userRemoteDataSource.signupUser(signupRequest = signupRequest)
//    }

class UserRepositoryImpl: UserRepository {
     override suspend fun loginUser(loginRequest: LoginRequest):
            Response<LoginResponse>?{
        return UserApi.getApi()?.loginUser(loginRequest=loginRequest)
    }
    override fun loginReissue(refreshToken:String):
            Response<LoginResponse>?{
        return UserApi.getApi()?.loginReissue(refreshToken ="Bearer ${refreshToken}" )
    }

    override suspend fun kakaoLogin(socialLoginRequest: SocialLoginRequest):
            Response<socialLoginResponse>?{
        return UserApi.getApi()?.socialLogin(socialType=2, socialLoginRequest=socialLoginRequest)
    }

    override suspend fun naverLogin(socialLoginRequest: SocialLoginRequest):
            Response<socialLoginResponse>?{
        return UserApi.getApi()?.socialLogin(socialType=3, socialLoginRequest=socialLoginRequest)
    }

    override suspend fun emailUser(emailRequest: EmailRequest):
            Response<EmailResponse>?{
        return UserApi.getApi()?.emailUser(emailRequest = emailRequest)
    }

    override suspend fun emailCheck(emailCheckRequest: EmailCheckRequest):
            Response<EmailCheckResponse>?{
        return UserApi.getApi()?.emailCheck(emailCheckRequest = emailCheckRequest)
    }

    override suspend fun signupUser(signupRequest: SignupRequest):
            Response<SignupResponse>?{
        return UserApi.getApi()?.signupUser(signupRequest = signupRequest)
    }
    override suspend fun phoneUser(phoneRequest: PhoneRequest):
            Response<PhoneResponse>?{
        return UserApi.getApi()?.phoneUser(phoneRequest = phoneRequest)
    }

    override suspend fun phoneCheck(phoneCheckRequest: PhoneCheckRequest):
            Response<PhoneCheckResponse>?{
        return UserApi.getApi()?.phoneCheck(phoneCheckRequest = phoneCheckRequest)
    }
}