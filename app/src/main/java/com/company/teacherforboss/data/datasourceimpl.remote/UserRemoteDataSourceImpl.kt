package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.UserRemoteDataSource
import com.company.teacherforboss.data.model.request.login.LoginRequest
import com.company.teacherforboss.data.model.response.login.LoginResponse
import com.company.teacherforboss.data.service.AuthService
import com.company.teacherforboss.data.model.request.login.SocialLoginRequest
import com.company.teacherforboss.data.model.response.login.socialLoginResponse
import com.company.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.company.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.company.teacherforboss.data.model.request.signup.EmailRequest
import com.company.teacherforboss.data.model.request.signup.NicknameRequest
import com.company.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.company.teacherforboss.data.model.request.signup.PhoneRequest
import com.company.teacherforboss.data.model.request.signup.SignupBossRequest
import com.company.teacherforboss.data.model.request.signup.SignupTeacherRequest
import com.company.teacherforboss.data.model.response.signup.EmailResponse
import com.company.teacherforboss.data.model.response.signup.NicknameResponse
import com.company.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.company.teacherforboss.data.model.response.signup.PhoneResponse
import com.company.teacherforboss.data.model.response.signup.SignupResponse
import retrofit2.Response

class UserRemoteDataSourceImpl(private val authService: AuthService): UserRemoteDataSource {
    override suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return authService.loginUser(loginRequest)
    }

    override suspend fun loginReissue(refreshToken: String): Response<LoginResponse>? {
        return authService.loginReissue(refreshToken)
    }

    override suspend fun kakaoLogin(socialLoginRequest: SocialLoginRequest): Response<socialLoginResponse>? {
        return authService.socialLogin(socialType = 2,socialLoginRequest)
    }

    override suspend fun naverLogin(socialLoginRequest: SocialLoginRequest): Response<socialLoginResponse>? {
        return authService.socialLogin(socialType = 3,socialLoginRequest)
    }

    override suspend fun emailUser(emailRequest: EmailRequest): Response<EmailResponse>? {
        return authService.emailUser(emailRequest)
    }

    override suspend fun emailCheck(emailCheckRequest: EmailCheckRequest): Response<EmailCheckResponse>? {
        return authService.emailCheck(emailCheckRequest)
    }

    override suspend fun signupUser(signupRequest: SignupBossRequest): Response<SignupResponse>? {
        return authService.signupBoss(signupRequest)
    }

    override suspend fun signupUser(signupRequest: SignupTeacherRequest): Response<SignupResponse>? {
        return authService.signupTeacher(signupRequest)
    }

    override suspend fun phoneUser(phoneRequest: PhoneRequest): Response<PhoneResponse>? {
        return authService.phoneUser(phoneRequest)
    }
    override suspend fun phoneCheck(phoneCheckRequest: PhoneCheckRequest): Response<PhoneCheckResponse>? {
        return authService.phoneCheck(phoneCheckRequest)
    }

    override suspend fun nicknameUser(nicknameRequest: NicknameRequest): Response<NicknameResponse>? {
        return authService.nicknameUser(nicknameRequest)
    }


}